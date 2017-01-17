import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Test2 {
	private JFrame details;
	private JPanel controlPanel;
	private JTextArea textArea;

	public static void main(String[] args) {
		Test2 obj = new Test2();
		for (int i = 1; i < 50; i++) {
			obj.method2(i);
		}

	}

	public Test2() {
		prepareGUI();
	}

	private void prepareGUI() {
		

		controlPanel = new JPanel();
		controlPanel.setSize(600, 800);
		textArea = new JTextArea();
		textArea.setAlignmentX(JLabel.CENTER);
		textArea.setSize(600, 800);
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(800, 600));
		controlPanel.add(scroll);
		details = new JFrame("sending packets details:");
		details.setSize(800, 800);
		details.setLayout(new GridLayout(1, 1));
		details.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		details.add(controlPanel);
		details.pack();
		textArea.append("This is string one");
		details.setVisible(true);
	}

	public void method2(int i) {
		textArea.append("\nThis is string :" + i);
	}

}
