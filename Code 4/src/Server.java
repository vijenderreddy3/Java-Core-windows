import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Server extends Thread
{
    private int port;
    private MainFrame frame;

    public Server(int port, MainFrame frame)
    {
        this.port = port;
        this.frame = frame;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try(OutputStream outputStream = new FileOutputStream("COSC635_P2_DataRecieved.txt"))
        {
            this.run(outputStream);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run(OutputStream outputStream) throws Exception
    {
        frame.append("Start run");
        DatagramSocket socket = new DatagramSocket(port);
        socket.setSoTimeout(1000);
        long startTime = System.currentTimeMillis();
        byte[] data = new byte[Config.PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        int nextPacketVersion = Config.FIRST_VERSION;
        for(boolean finished = false;!finished;)
        {
            try
            {
                socket.receive(packet);
            }catch(Exception e)
            {
                continue;
            }
            
            int packetVersion = 256 * (256 * (256 * (data[0] + 128) + data[1] + 128) + data[2] + 128) + data[3] + 128;
            if(packetVersion == Config.LAST_VERSION)
            {
                nextPacketVersion = Config.FIRST_VERSION;
            }else if(packetVersion == nextPacketVersion)
            {
                outputStream.write(Arrays.copyOfRange(data, Config.VERSION_SIZE, packet.getLength()));
                frame.append("Got packet " + nextPacketVersion);
                nextPacketVersion++;
            }else
            {
                frame.append("Unwanted packet " + packetVersion);
                packetVersion = nextPacketVersion - 1;
            }
            
            byte[] responseData = new byte[4];
            responseData[0] = (byte) (packetVersion / (256*256*256) -128);
            responseData[1] = (byte) (packetVersion / (256*256) -128);
            responseData[2] = (byte) (packetVersion / (256) -128);
            responseData[3] = (byte) (packetVersion % 256 -128);
            DatagramPacket ackPacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
            socket.send(ackPacket);
            finished = (packetVersion == Config.LAST_VERSION);
        }
        long time = System.currentTimeMillis() - startTime;
        frame.append("Run time: " + time + "ms");
        socket.close();
        frame.append("Finish run");
    }
}
