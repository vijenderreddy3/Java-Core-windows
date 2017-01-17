package chess.client.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chess.client.control.Controller;

/**
 * 
 * This panel is responsible for showing a UI for user registering.
 *
 */
public class RegisterPanel extends JPanel
{
    private final JTextField usernameField = new JTextField(10);
    private final JTextField hostField = new JTextField(10);
    private final JTextField portField = new JTextField(10);
    private final JButton goButton = new JButton("Go");
    
    public RegisterPanel(Controller controller)
    {
        this.setLayout(new GridLayout(4, 1));
        
        this.add(this.createLabelledPanel("Host: ", hostField));
        this.add(this.createLabelledPanel("Port: ", portField));
        this.add(this.createLabelledPanel("Username: ", usernameField));
        
        JPanel buttonsPanel = new JPanel();
        this.add(buttonsPanel);
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(goButton);
        goButton.addActionListener(e -> controller.go(
                hostField.getText(), 
                Integer.parseInt(portField.getText()), 
                usernameField.getText()));
    }
    
    public JTextField getUsernameField()
    {
        return usernameField;
    }

    public JTextField getHostField()
    {
        return hostField;
    }

    public JTextField getPortField()
    {
        return portField;
    }

    private JPanel createLabelledPanel(String label, Component component)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }
}
