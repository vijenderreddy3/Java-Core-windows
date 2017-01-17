import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GBNTransferer
{

    public void send(String address, int port, int lossPercentage, int gbnWindowSize, InputStream inputStream, MainFrame frame)
    {
        frame.append("GBN");
        int version = Config.FIRST_VERSION;
        List<byte[]> packetsToSend = new ArrayList<>();
        long time = System.currentTimeMillis();
        try (DatagramSocket socket = new DatagramSocket())
        {
            socket.setSoTimeout(1000);
            InetAddress ip = InetAddress.getByName(address);
            
            int sent = 0;
            int lost = 0;
            while(true)
            {
                while(packetsToSend.size() < gbnWindowSize)
                {
                    byte[] bytes = new byte[Config.PACKET_SIZE];
                    bytes = new CommonTool().readBytes(inputStream, bytes, Config.VERSION_SIZE);
                    if(bytes != null)
                    {
                        packetsToSend.add(bytes);
                    }else
                    {
                        break;
                    }
                }
                if(packetsToSend.isEmpty())
                {
                    version = Config.LAST_VERSION;
                    packetsToSend.add(new byte[Config.VERSION_SIZE]);
                }
                for(int packetIndex = 0;packetIndex < packetsToSend.size();packetIndex++)
                {
                    byte[] bytes = packetsToSend.get(packetIndex);
                    int byteSize = bytes.length;
                    int packetVersion = version + packetIndex;
                    byte[] versionBytes = new CommonTool().toBytes(packetVersion);
                    System.arraycopy(versionBytes, 0, bytes, 0, versionBytes.length);
                    if(new Random().nextInt(100) < lossPercentage)
                    {
                        lost += byteSize;
                        frame.append("Lost " + packetVersion);
                    }else
                    {
                        socket.send(new DatagramPacket(bytes, byteSize, ip, port));
                        frame.append("Sent " + packetVersion);
                    }
                    sent += byteSize;
                }

                byte[] bytes = new byte[Config.VERSION_SIZE];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                int biggestVersion = Integer.MIN_VALUE;
                while(true)
                {
                    try
                    {
                        socket.receive(packet);
                        bytes = packet.getData();
                        int packetVersion = new CommonTool().toVersion(bytes);
                        if(packetVersion > biggestVersion)
                        {
                            biggestVersion = packetVersion;
                        }
                    }catch(Exception e)
                    {
                        break;
                    }
                }
                for(;!packetsToSend.isEmpty()&& version <= biggestVersion;version++)
                {
                    packetsToSend.remove(0);
                }
                if(biggestVersion == Config.LAST_VERSION)
                {
                    break;
                }
            }
            frame.append("Sent: " + sent);
            frame.append("Lost: " + lost);
            frame.append("Time: " + (System.currentTimeMillis() - time) + "ms");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
