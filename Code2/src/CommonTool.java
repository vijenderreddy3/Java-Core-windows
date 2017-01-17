import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CommonTool
{
    public byte[] readBytes(InputStream inputStream, byte[] bytes, int startPosition)
    {
        try
        {
            int readSize = inputStream.read(bytes, startPosition, bytes.length - startPosition);
            int size = startPosition + readSize;
            if(readSize < 0)
            {
                return null;
            }else if(size < bytes.length)
            {
                return Arrays.copyOf(bytes, size);
            }else
            {
                return bytes;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public int toVersion(byte[] bytes)
    {
        int version = 0;
        for(int index = 0;index < Config.VERSION_SIZE;index++)
        {
            version = version * 256 + ((int) bytes[index] - Byte.MIN_VALUE);
        }
        return version;
    }
    
    public byte[] toBytes(int version)
    {
        byte[] bytes = new byte[Config.VERSION_SIZE];
        for(int index = Config.VERSION_SIZE - 1;index >= 0;index--)
        {
            bytes[index] = (byte) (version % 256 + Byte.MIN_VALUE);
            version /= 256;
        }
        return bytes;
    }
}
