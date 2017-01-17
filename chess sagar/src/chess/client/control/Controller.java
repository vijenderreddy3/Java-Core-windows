package chess.client.control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import chess.client.ui.MainFrame;
import chess.client.ui.RegisterPanel;
import chess.protocol.Request;
import chess.protocol.Response;
import chess.protocol.game.Game;
import chess.protocol.user.RegisterUserRequest;
import chess.protocol.user.RegisterUserResponse;
import chess.protocol.user.User;

/**
 * 
 * This class is responsible for controlling and coordinating the whole process.
 *
 */
public class Controller
{
    private static final String[] NAMES = {
        "Jack", "Rose", "Peter", "Victor", "Jason", "Aimomd", "Walter", "Helen", "John", "Matt", "Johnson",
        "Arnaud", "Chloe", "James", "Charlotte", "Jacob", "Sarah", "Luke", "Emily", "Jonah", "Kellie", 
        "Shawn", "Lily", "Harry", "Aaliyah", "David", "Elizabeth", "Aaron", "Sophia", "Kevin", "Emma", 
        "Matthew", "Abigail", "Muhammad", "Ava", "Dylan", "Jennifer", "Jack", "Rebecca", "Daniel", 
        "Isabella", "Joshua", "Olivia", "Liam", "Samantha", "Alex", "Mia", "Christopher", "Hannah", 
        "Alexander", "Lauren", "Michael", "Grace", "Zayn", "Jessica", "Jordan", "Ashley", "Ryan", "Ella", 
        "Adam", "Savannah", "Ethan"};

    private String host;
    private int port;
    private final MainFrame mainFrame = new MainFrame(this);
    private User user;
    private Game game;
    
    public Controller(String host, int port)
    {
        this.host = host;
        this.port = port;
        mainFrame.setSize(300, 150);
        
        RegisterPanel registerPanel = mainFrame.getRegisterPanel();
        mainFrame.setCentralPanel(registerPanel);
        registerPanel.getHostField().setText(host);
        registerPanel.getPortField().setText(String.valueOf(port));
        String name = NAMES[new Random().nextInt(NAMES.length)];
        registerPanel.getUsernameField().setText(name);
        
        mainFrame.setVisible(true);
    }

    public MainFrame getMainFrame()
    {
        return mainFrame;
    }
    
    public User getUser()
    {
        return user;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public void go(String host, int port, String username)
    {
        this.host = host;
        this.port = port;
        
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername(username);
        RegisterUserResponse response = this.sendRequest(request);
        this.user = response.getUser();
        
        mainFrame.setTitle(username);
        mainFrame.setSize(800, 700);
        mainFrame.setCentralPanel(mainFrame.getGamePanel());
        
        new Thread(new GamesUpdater(this)).start();
        new Thread(new BoardUpdater(this)).start();
    }
    
    @SuppressWarnings("unchecked")
    public <REQ extends Request, RES extends Response> RES sendRequest(REQ request)
    {
        try(Socket socket = new Socket(host, port))
        {
            if(user != null)
            {
                request.setUserId(user.getId());
            }
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return (RES) inputStream.readObject();
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
