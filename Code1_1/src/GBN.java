import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GBN implements Sender {

	private final Random r = new Random(System.currentTimeMillis());
	private final int loss;
	private final MainFrame frame;
	private final int window;
	private final List<byte[]> packets = new LinkedList<>();

	public GBN(int loss, int window, MainFrame frame) {
		this.loss = loss;
		this.window = window;
		this.frame = frame;
	}

	@Override
	public void send(String host, int port, InputStream inputStream) {
		frame.append("Start sending GBN...");
		packets.clear();
		long start = System.currentTimeMillis();
		int sent = 0;
		int lost = 0;
		int v = Config.FIRST_VERSION;
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(500);
			while (true) {
				while (packets.size() < window) {
					byte[] data = new byte[Config.PACKET_SIZE];
					data = Utilities.read(inputStream, data,
							Config.VERSION_SIZE);
					if (data == null) {
						break;
					}
					packets.add(data);
				}

				if (packets.size() == 0) {
					packets.add(new byte[Config.VERSION_SIZE]);
					v = Config.LAST_VERSION;
				}

				for (int i = 0; i < packets.size(); i++) {
					byte[] data = packets.get(i);
					int version = v + i;
					byte[] vb = Utilities.fromVersion(version);
					System.arraycopy(vb, 0, data, 0,
							vb.length);
					int length = data.length;
					sent += length;

					int randomNumber = r.nextInt(100);
					if (randomNumber < loss) {
						lost += length;
						frame.append("Lost package " + version);
					} else {
						frame.append("Sending package " + version + " (" + length + ")");
						InetAddress ip = InetAddress.getByName(host);
						DatagramPacket sendPacket = new DatagramPacket(data,
								length, ip, port);
						socket.send(sendPacket);
					}
				}

				int maxV = Integer.MIN_VALUE;
				byte[] responseData = new byte[Config.VERSION_SIZE];
				DatagramPacket responsePacket = new DatagramPacket(responseData,
						responseData.length);
				while (true) {
					try {
						socket.receive(responsePacket);
						responseData = responsePacket.getData();
						int rV = Utilities.toVersion(responseData);
						if (rV > maxV) {
							maxV = rV;
						}
					} catch (IOException e) {
						break;
					}
				}

				if (maxV == Config.LAST_VERSION) {
					break;
				}

				for (; v <= maxV; v++) {
					packets.remove(0);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		frame.append("Total Time: " + (end - start) + "ms");
		frame.append("Sent Bytes: " + sent);
		frame.append("Lost Bytes: " + lost);
	}

}
