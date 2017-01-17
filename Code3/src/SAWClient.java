import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

@SuppressWarnings("resource")
public class SAWClient extends PackageOwner
{

    public void send(String server, int port, int missRate, 
            InputStream inputStream, MainFrame frame)
    {
        frame.append("SAWClient.send()");
        long time = System.currentTimeMillis();
        try
        {
            InetAddress ip = InetAddress.getByName(server);
            this.send(ip, port, missRate, inputStream, frame);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        frame.append("Time: " + (System.currentTimeMillis() - time) + "ms");
    }

    public void send(InetAddress ip, int port, int missRate, 
            InputStream inputStream, MainFrame frame) throws IOException
    {
        DatagramSocket socket = new DatagramSocket();
        int nextVersion = Config.FIRST_VERSION;
        socket.setSoTimeout(1000);
        int send = 0;
        int miss = 0;
        
        

        DatagramPacket receivedPacket = new DatagramPacket(new byte[Config.PACKET_SIZE], Config.PACKET_SIZE);
        while(true)
        {
            byte[] bytes = new byte[Config.PACKET_SIZE];
            int size = inputStream.read(bytes, Config.VERSION_SIZE, bytes.length - Config.VERSION_SIZE);
            size += Config.VERSION_SIZE;
            if(size < Config.VERSION_SIZE)
            {
                size = Config.VERSION_SIZE;
                nextVersion = Config.LAST_VERSION;
                bytes = new byte[Config.VERSION_SIZE];
            }
            
            

            for(;;)
            {
                send += size;
                if(new Random().nextInt(100) < missRate)
                {
                    miss += size;
                    frame.append("Miss " + nextVersion);
                }else
                {
                    byte[] nextVersionBytes = this.versionToBytes(nextVersion);
                    System.arraycopy(nextVersionBytes, 0, bytes, 0, nextVersionBytes.length);
                    socket.send(new DatagramPacket(bytes, size, ip, port));
                    frame.append("Send " + nextVersion);
                }
                try
                {
                    socket.receive(receivedPacket);
                    break;
                }catch(IOException e)
                {
                    ;
                }
            }
            
            
            
            nextVersion++;
            if(nextVersion >= Config.LAST_VERSION)
            {
                break;
            }
        }
        frame.append("Send: " + send);
        frame.append("Miss: " + miss);
    }
}
