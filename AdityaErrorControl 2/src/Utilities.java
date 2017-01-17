import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Utilities {

	private Utilities() {

	}

	public static byte[] fromVersion(int v) {
		byte[] b = new byte[Config.VERSION_SIZE];
		int r = v;
		for (int i = Config.VERSION_SIZE - 1; i >= 0; i--) {
			b[i] = (byte) (r % 256 + Byte.MIN_VALUE);
			r /= 256;
		}
		return b;
	}

	public static int toVersion(byte[] b) {
		int v = 0;
		for (int i = 0; i < Config.VERSION_SIZE; i++) {
			v = v * 256 + ((int) b[i] - Byte.MIN_VALUE);
		}
		return v;
	}

	public static byte[] read(InputStream inputStream, byte[] b, int s) {
		try {
			int length = inputStream.read(b, s, b.length - s);
			if (length < 0) {
				return null;
			}

			int size = s + length;
			if (size < b.length) {
				return Arrays.copyOf(b, size);
			} else {
				return b;
			}
		} catch (IOException e) {
			return null;
		}
	}
}
