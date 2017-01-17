import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("resource")
public class GBNClient extends PackageOwner
{
    public void send(String server, int port, int missRate, 
            int window, InputStream inputStream, MainFrame frame)
    {
        frame.append("GBNClient.send()");
        long beginTime = System.currentTimeMillis();
        try
        {
            InetAddress ip = InetAddress.getByName(server);
            this.send(ip, port, missRate, window, inputStream, frame);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        frame.append("Time: " + (System.currentTimeMillis() - beginTime) + "ms");
    }

    public void send(InetAddress ip, int port, int missRate, 
            int window, InputStream inputStream, MainFrame frame) throws IOException
    {
        DatagramSocket socket = new DatagramSocket();
        int version = Config.FIRST_VERSION;
        List<byte[]> nextPackages = new ArrayList<>();
        socket.setSoTimeout(1000);
        int send = 0;
        int miss = 0;
        byte[] bytes = new byte[Config.PACKET_SIZE];
        
        
        
        while(true)
        {
            int size;
            while(nextPackages.size() < window&&
                    (size = inputStream.read(bytes, Config.VERSION_SIZE, bytes.length - Config.VERSION_SIZE)) >= 0)
            {
                nextPackages.add(Arrays.copyOf(bytes, Config.VERSION_SIZE + size));
            }
            if(nextPackages.isEmpty())
            {
                version = Config.LAST_VERSION;
                nextPackages.add(new byte[Config.VERSION_SIZE]);
            }
            
            
            
            
            for(int nextPackageI = 0;nextPackageI < nextPackages.size();nextPackageI++)
            {
                byte[] nextPackage = nextPackages.get(nextPackageI);
                send += nextPackage.length;
                int nextPackageVersion = version + nextPackageI;
                byte[] nextVersionBytes = this.versionToBytes(nextPackageVersion);
                System.arraycopy(nextVersionBytes, 0, nextPackage, 0, nextVersionBytes.length);
                if(Math.random()*100 > missRate)
                {
                    socket.send(new DatagramPacket(nextPackage, nextPackage.length, ip, port));
                    frame.append("Send " + nextPackageVersion);
                }else
                {
                    miss += nextPackage.length;
                    frame.append("Miss " + nextPackageVersion);
                }
            }

            DatagramPacket receivedPackage = new DatagramPacket(new byte[Config.VERSION_SIZE], Config.VERSION_SIZE);
            int lastVersion = -1;
            for(int windowI = 0;windowI < window;windowI++)
            {
                try
                {
                    socket.receive(receivedPackage);
                }catch(Exception e)
                {
                    break;
                }
                byte[] receivedVersionBytes = receivedPackage.getData();
                int receivedVersion = this.bytesToVersion(receivedVersionBytes);
                lastVersion = (lastVersion < receivedVersion ? receivedVersion : lastVersion);
            }
            if(lastVersion == Config.LAST_VERSION)
            {
                break;
            }
            for(;version <= lastVersion;version++)
            {
                nextPackages.remove(0);
            }
        }
        
        
        
        frame.append("Miss: " + miss);
        frame.append("Send: " + send);
    }

}
