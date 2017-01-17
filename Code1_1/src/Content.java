
public class Content {

	private final int version;
	private final byte[] content;

	public Content(byte[] versionAndData) {
		version = Utilities.toVersion(versionAndData);
		content = new byte[versionAndData.length - Config.VERSION_SIZE];
		System.arraycopy(versionAndData, Config.VERSION_SIZE, content, 0,
				content.length);
	}

	public Content(int version, byte[] data) {
		this.version = version;
		this.content = data;
	}

	public int getVersion() {
		return version;
	}

	public byte[] getData() {
		return content;
	}
}
