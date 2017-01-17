import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	public static void main(String[] strings) {
		new MainFrame().setVisible(true);
	}

	private final TextArea area = new TextArea();
	private final JTextField hostField = new JTextField(10);
	private final JTextField portField = new JTextField(5);
	private final JTextField lossField = new JTextField(5);
	private final JButton receiveButton = new JButton("Receive");
	private final JButton sawButton = new JButton("SAW");
	private final JButton gbnButton = new JButton("GBN");

	public MainFrame() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		this.add(new JScrollPane(area), BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(new JLabel("Host"));
		southPanel.add(hostField);
		hostField.setText("localhost");
		southPanel.add(new JLabel("Port"));
		southPanel.add(portField);
		portField.setText("2345");
		southPanel.add(new JLabel("Loss percentage"));
		southPanel.add(lossField);
		lossField.setText("50");
		southPanel.add(receiveButton);
		receiveButton.addActionListener(listener);
		southPanel.add(sawButton);
		sawButton.addActionListener(listener);
		southPanel.add(gbnButton);
		gbnButton.addActionListener(listener);
	}

	private final ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String host = hostField.getText();
				int port;
				int loss;
				try {
					port = Integer.parseInt(portField.getText());
					loss = Integer.parseInt(lossField.getText());
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}

				Object source = e.getSource();
				if(source == receiveButton)
				{
					Server server = new Server();
					server.port = port;
					server.frame = MainFrame.this;
					new Thread(server).start();
				}else
				{
					try (InputStream inputStream = new FileInputStream("COSC635_P2_DataSent.txt")) {
					    if (source == sawButton) {
					        new SAWClient().send(host, port, loss, inputStream, MainFrame.this);
					    } else {
					        new GBNClient().send(host, port, loss, Config.GBN_WINDOW_SIZE, inputStream, MainFrame.this);
					    }
					} catch (Exception e2) {
						throw new RuntimeException(e2);
					}
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(MainFrame.this, "Invalid input!");
			}
		}
	};

	public void append(String text) {
		area.append(text + "\n");
	}
}
