public class Packet
{
    private final int version;
    private final byte[] bytes;

    public Packet(byte[] bytes)
    {
        this.bytes = new byte[bytes.length - Config.VERSION_SIZE];
        System.arraycopy(bytes, Config.VERSION_SIZE, this.bytes, 0, this.bytes.length);
        version = new CommonTool().toVersion(bytes);
    }

    public int getVersion()
    {
        return version;
    }

    public byte[] getBytes()
    {
        return bytes;
    }
}
