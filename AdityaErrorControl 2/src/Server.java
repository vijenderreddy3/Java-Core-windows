import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server implements Runnable {
	private final int port;
	private final String host;
	private final MainFrame frame;
	private int nextV = Config.FIRST_VERSION;

	public Server(int port, MainFrame frame,String host) {
		this.frame = frame;
		this.host=host;
		this.port = port;
		new Thread(this).start();
	}

	@Override
	public void run() {
		byte[] data = new byte[Config.PACKET_SIZE];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try (OutputStream outputStream = new FileOutputStream(
				"COSC635_P2_DataRecieved.txt");
				DatagramSocket socket = new DatagramSocket(port);) {
			socket.setSoTimeout(1000);
			while (true) {
				try {
					socket.receive(packet);
					byte[] bytes = Arrays.copyOf(packet.getData(),
							packet.getLength());
					Content content = new Content(bytes);

					int v = content.getVersion();
					if (v == Config.LAST_VERSION) {
						frame.append("File receive completed.");
						nextV = Config.FIRST_VERSION;
					} else if (v == nextV) {
						frame.append("Received packet " + nextV + " ("
								+ content.getData().length + ")");
						outputStream.write(content.getData());
						nextV++;
					} else {
						frame.append("Received unknown packet " + v);
						v = nextV - 1;
					}

					byte[] responseData = Utilities.fromVersion(v);
					InetAddress ip = packet.getAddress();
					int port = packet.getPort();
					DatagramPacket responsePacket = new DatagramPacket(
							responseData, responseData.length, ip, port);
					socket.send(responsePacket);

					if (v == Config.LAST_VERSION) {
						break;
					}
				} catch (Exception e) {
					;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
