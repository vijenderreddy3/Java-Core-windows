import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server extends PackageOwner implements Runnable
{
    public int port;
    public MainFrame frame;

    @Override
    public void run()
    {
        frame.append("Server.receive()");
        try
        {
            this.receive(frame, port);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void receive(MainFrame frame, int port) throws Exception
    {
        DatagramSocket socket = new DatagramSocket(port);
        socket.setSoTimeout(500);
        OutputStream stream = new FileOutputStream("COSC635_P2_DataRecieved.txt");

        int nextVersion = Config.FIRST_VERSION;
        DatagramPacket packet = new DatagramPacket(new byte[Config.PACKET_SIZE], Config.PACKET_SIZE);
        while(true)
        {
            try
            {
                socket.receive(packet);                
            }catch(Exception e)
            {
                continue;
            }
            
            byte[] bytes = Arrays.copyOf(packet.getData(), packet.getLength());
            int version = this.bytesToVersion(bytes);
            if(version == nextVersion)
            {
                nextVersion++;
                frame.append("Receive " + nextVersion);
                stream.write(bytes, Config.VERSION_SIZE, bytes.length - Config.VERSION_SIZE);
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
            int clientPort = packet.getPort();
            byte[] backData = this.versionToBytes(version);
            DatagramPacket backPacket = new DatagramPacket(backData, backData.length, ip, clientPort);
            socket.send(backPacket);
            if(version == Config.LAST_VERSION)
            {
                break;
            }
        }
        stream.close();
        socket.close();
    }
}
