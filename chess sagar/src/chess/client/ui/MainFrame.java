package chess.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.client.control.Controller;

/**
 * 
 * This frame is the main UI entrance.
 *
 */
public class MainFrame extends JFrame
{
    @SuppressWarnings("unused")
    private final Controller controller;
    private final RegisterPanel registerPanel;
    private final GamePanel gamePanel;
    private JPanel centralPanel;
    
    public MainFrame(Controller controller)
    {
        this.controller = controller;
        
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());
        
        registerPanel = new RegisterPanel(controller);
        gamePanel = new GamePanel(controller);
        
        this.setCentralPanel(registerPanel);
    }
    
    public RegisterPanel getRegisterPanel()
    {
        return registerPanel;
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public void setCentralPanel(JPanel panel)
    {
        if(centralPanel != null)
        {
            this.remove(centralPanel);
        }
        
        if(panel != null)
        {
            this.add(panel, BorderLayout.CENTER);
        }
        centralPanel = panel;
        
        int width = this.getWidth();
        int height = this.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - width)/2, (screenSize.height - height)/2, width, height);
    }
}
