import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	public static void main(String[] strings) {
		new MainFrame().setVisible(true);
	}

	private final TextArea area = new TextArea();
	private final JButton sawButton = new JButton("SAW");
	private final JButton gbnButton = new JButton("GBN");
	private final JFileChooser chooser = new JFileChooser(".");

	public MainFrame() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		this.add(new JScrollPane(area), BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		this.add(buttonsPanel, BorderLayout.SOUTH);
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(sawButton);
		sawButton.addActionListener(listener);
		buttonsPanel.add(gbnButton);
		gbnButton.addActionListener(listener);
	}

	private final ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Server(Config.PORT, MainFrame.this,Config.HOST);
			Sender client;
			if (e.getSource() == sawButton) {
				client = new SAW(Config.LOSS, MainFrame.this);
			} else {
				client = new GBN(Config.LOSS, Config.GBN_WINDOW_SIZE, MainFrame.this);
			}

			int option = chooser.showOpenDialog(MainFrame.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try (InputStream inputStream = new FileInputStream(file)) {
					client.send(Config.HOST, Config.PORT, inputStream);
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		}
	};

	public void append(String text) {
		area.append(text + "\n");
	}
}
