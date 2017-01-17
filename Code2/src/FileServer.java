import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class FileServer extends Thread
{
    private int port;
    private MainFrame frame;

    public void start(int port, MainFrame frame)
    {
        this.frame = frame;
        this.port = port;
        this.start();
    }

    @Override
    public void run()
    {
        frame.append("Server started!");
        try
        {
            OutputStream output = new FileOutputStream("COSC635_P2_DataRecieved.txt"); 
            @SuppressWarnings("resource")
            DatagramSocket socket = new DatagramSocket(port);
            socket.setSoTimeout(1000);

            int nextVersion = Config.FIRST_VERSION;
            DatagramPacket packet = new DatagramPacket(new byte[Config.PACKET_SIZE], Config.PACKET_SIZE);
            while(true)
            {
                try
                {
                    socket.receive(packet);
                    byte[] bytes = Arrays.copyOf(packet.getData(), packet.getLength());
                    Packet myPacket = new Packet(bytes);
                    int version = myPacket.getVersion();
                    if(version == nextVersion)
                    {
                        nextVersion++;
                        frame.append("Receive " + nextVersion);
                        output.write(myPacket.getBytes());
                    }else if(version == Config.LAST_VERSION)
                    {
                        nextVersion = Config.FIRST_VERSION;
                        frame.append("Complete");
                    }else
                    {
                        version = nextVersion - 1;
                        frame.append("Unknown " + version);
                    }
                    InetAddress ip = packet.getAddress();
                    int port = packet.getPort();
                    byte[] backData = new CommonTool().toBytes(version);
                    DatagramPacket backPacket = new DatagramPacket(backData, backData.length, ip, port);
                    socket.send(backPacket);
                    if(version == Config.LAST_VERSION)
                    {
                        break;
                    }
                }catch(Exception e)
                {
                    ;
                }
            }
            output.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
