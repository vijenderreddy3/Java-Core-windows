import java.io.InputStream;

public interface Sender {
	void send(String host, int port, InputStream inputStream);
}
