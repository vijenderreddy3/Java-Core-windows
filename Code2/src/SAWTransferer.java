import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class SAWTransferer
{

    public void send(String address, int port, int lossPercentage, InputStream inputStream, MainFrame frame)
    {
        frame.append("SAW");
        int packetVersion = Config.FIRST_VERSION;
        long time = System.currentTimeMillis();
        try (DatagramSocket socket = new DatagramSocket())
        {
            socket.setSoTimeout(1000);
            InetAddress ip = InetAddress.getByName(address);
            
            int sent = 0;
            int lost = 0;
            while(true)
            {
                byte[] bytes = new byte[Config.PACKET_SIZE];
                int packetSize = inputStream.read(bytes, Config.VERSION_SIZE, bytes.length - Config.VERSION_SIZE);
                if(packetSize < 0)
                {
                    packetVersion = Config.LAST_VERSION;
                    packetSize = Config.VERSION_SIZE;
                    bytes = new byte[Config.VERSION_SIZE];
                }else
                {
                    packetSize += Config.VERSION_SIZE;
                }
                do
                {
                    byte[] versionBytes = new CommonTool().toBytes(packetVersion);
                    System.arraycopy(versionBytes, 0, bytes, 0, versionBytes.length);

                    if(new Random().nextInt(100) < lossPercentage)
                    {
                        lost += packetSize;
                        frame.append("Lost " + packetVersion);
                    }else
                    {
                        socket.send(new DatagramPacket(bytes, packetSize, ip, port));
                        frame.append("Sent" + packetVersion);
                    }
                    sent += packetSize;
                    byte[] returnBytes = new byte[Config.VERSION_SIZE];
                    DatagramPacket packet = new DatagramPacket(returnBytes, returnBytes.length);
                    try
                    {
                        socket.receive(packet);
                        break;
                    }catch(IOException e)
                    {
                        ;
                    }
                }while(true);
                if(packetVersion == Config.LAST_VERSION)
                {
                    break;
                }else
                {
                    packetVersion++;
                }
            }
            frame.append("Time: " + (System.currentTimeMillis() - time) + "ms");
            frame.append("Sent: " + sent);
            frame.append("Lost: " + lost);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
