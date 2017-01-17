
public class PackageOwner
{
    public int bytesToVersion(byte[] bytes)
    {
        int version = 0;
        for(int index = 0;index < Config.VERSION_SIZE;index++)
        {
            version = version * 256 + bytes[index] - Byte.MIN_VALUE;
        }
        return version;
    }
    
    public byte[] versionToBytes(int version)
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
