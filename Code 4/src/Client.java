import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client
{
    private InetAddress serverIp;
    private int serverPort;
    private int packetLoss;
    private MainFrame frame;

    public Client(String serverHost, int serverPort, int packetLoss, MainFrame frame) throws Exception
    {
        serverIp = InetAddress.getByName(serverHost);
        this.serverPort = serverPort;
        this.packetLoss = packetLoss;
        this.frame = frame;
    }

    public void gbn(File file, int window)
    {
        try(InputStream inputStream = new FileInputStream(file))
        {
            this.gbn(inputStream, window);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void gbn(InputStream inputStream, int windowSize) throws Exception
    {
        frame.append("Start GBN");
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(500);
        List<byte[]> packetsToBeSent = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        int packetsSentCount = 0;
        int packetsLostCount = 0;
        int packetVersion = Config.FIRST_VERSION;
        for(boolean finished = false;!finished;)
        {
            byte[] buffer = new byte[Config.PACKET_SIZE];
            for(int length;packetsToBeSent.size() < windowSize&&
                    (length = inputStream.read(buffer, Config.VERSION_SIZE, buffer.length - Config.VERSION_SIZE)) >= 0;)
            {
                packetsToBeSent.add(Arrays.copyOf(buffer, Config.VERSION_SIZE + length));
            }
            if(packetsToBeSent.isEmpty())
            {
                packetsToBeSent.add(new byte[Config.VERSION_SIZE]);
                packetVersion = Config.LAST_VERSION;
            }

            int sendVersion = packetVersion;
            for(byte[] packetToBeSent : packetsToBeSent)
            {
                int length = packetToBeSent.length;
                packetsSentCount += length;

                if(Math.random() < packetLoss / 100d)
                {
                    packetsLostCount += length;
                    frame.append("Lost packet " + sendVersion);
                }else
                {
                    packetToBeSent[0] = (byte) (sendVersion / (256*256*256) -128);
                    packetToBeSent[1] = (byte) (sendVersion / (256*256) -128);
                    packetToBeSent[2] = (byte) (sendVersion / (256) -128);
                    packetToBeSent[3] = (byte) (sendVersion % 256 -128);
                    frame.append("Send packet " + sendVersion);
                    DatagramPacket sendPacket = new DatagramPacket(packetToBeSent, length, serverIp, serverPort);
                    socket.send(sendPacket);
                }
                sendVersion++;
            }

            int maximumACK = -1;
            try
            {
                for(buffer = new byte[Config.VERSION_SIZE];;)
                {
                    socket.receive(new DatagramPacket(buffer, buffer.length));
                    int ack = 256*(256*(256*(buffer[0] + 128) + buffer[1] + 128) 
                            + buffer[2] + 128) + buffer[3] + 128;
                    if(ack > maximumACK)
                    {
                        maximumACK = ack;
                    }
                }
            }catch(Exception e)
            {
                ;
            }

            if(maximumACK == Config.LAST_VERSION)
            {
                finished = true;
                continue;
            }else
            {
                for(int i = 0;i < maximumACK - packetVersion + 1;i++)
                {
                    packetsToBeSent.remove(0);
                }
                packetVersion = maximumACK + 1;
            }
        }
        long time = System.currentTimeMillis() - startTime;
        frame.append("Data sent: " + packetsSentCount);
        frame.append("Data lost: " + packetsLostCount);
        frame.append("Time spent: " + time + "ms");
        socket.close();
        frame.append("Finish GBN");
    }

    public void saw(File file)
    {
        try(InputStream inputStream = new FileInputStream(file))
        {
            this.saw(inputStream);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saw(InputStream inputStream) throws Exception
    {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(500);
        long startTime = System.currentTimeMillis();
        frame.append("Start SAW");
        int packetVersion = Config.FIRST_VERSION;
        int packetsSentCount = 0;
        int packetsLostCount = 0;
        byte[] buffer = new byte[Config.PACKET_SIZE];
        for(boolean finished = false;!finished;packetVersion++)
        {
            int length = inputStream.read(buffer, Config.VERSION_SIZE, buffer.length - Config.VERSION_SIZE);
            if(length < 0)
            {
                length = Config.VERSION_SIZE;
                packetVersion = Config.LAST_VERSION;
                buffer = new byte[Config.VERSION_SIZE];
            }else
            {
                length += Config.VERSION_SIZE;
            }
            buffer[0] = (byte) (packetVersion / (256*256*256) -128);
            buffer[1] = (byte) (packetVersion / (256*256) -128);
            buffer[2] = (byte) (packetVersion / (256) -128);
            buffer[3] = (byte) (packetVersion % 256 -128);

            for(boolean acked = false;!acked;)
            {
                packetsSentCount += length;
                if(Math.random() < packetLoss / 100d)
                {
                    packetsLostCount += length;
                    frame.append("Lost packet " + packetVersion);
                }else
                {
                    DatagramPacket packet = new DatagramPacket(buffer, length, serverIp, serverPort);
                    socket.send(packet);
                    frame.append("Send packet " + packetVersion);
                }

                try
                {
                    socket.receive(new DatagramPacket(new byte[4], 4));
                    acked = true;
                }catch(IOException e)
                {
                    ;
                }
            }

            if(packetVersion == Config.LAST_VERSION)
            {
                finished = true;
            }
        }

        long time = System.currentTimeMillis() - startTime;
        frame.append("Data sent: " + packetsSentCount);
        frame.append("Data lost: " + packetsLostCount);
        frame.append("Time spent: " + time + "ms");
        socket.close();
        frame.append("Finish SAW");
    }

}
