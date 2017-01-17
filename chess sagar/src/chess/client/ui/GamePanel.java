package chess.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import chess.client.control.Controller;
import chess.protocol.game.CreateGameRequest;
import chess.protocol.game.CreateGameResponse;
import chess.protocol.game.Game;
import chess.protocol.game.JoinGameRequest;
import chess.protocol.game.JoinGameResponse;
import chess.protocol.game.QuitGameRequest;
import chess.protocol.user.User;

/**
 * 
 * This panel is responsible for drawing the game list, the operation buttons and also the chess board.
 *
 */
public class GamePanel extends JPanel
{
    private final JList<Game> gameList = new JList<>();
    private final BoardPanel boardPanel;
    private final JButton createButton = new JButton("Create");
    private final JButton joinButton = new JButton("Join");
    private final JButton giveUpButton = new JButton("Give up");
    
    private final Controller controller;
    
    public GamePanel(Controller controller)
    {
        this.controller = controller;
        
        this.setLayout(new BorderLayout());
        
        this.add(gameList, BorderLayout.WEST);
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameList.addListSelectionListener(e -> onSelection(e.getFirstIndex()));

        boardPanel = new BoardPanel(controller);
        boardPanel.setSize(500, 500);
        this.add(boardPanel, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        this.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(createButton);
        createButton.addActionListener(e -> createGame(controller.getUser().getUsername() + "'s Game"));
        buttonsPanel.add(joinButton);
        joinButton.addActionListener(e -> joinGame());
        buttonsPanel.add(giveUpButton);
        giveUpButton.addActionListener(e -> quitGame());
    }
    
    public BoardPanel getBoardPanel()
    {
        return boardPanel;
    }

    public void refreshGames(Collection<Game> games)
    {
        Vector<Game> vector = new Vector<>(games);
        gameList.setListData(vector);
        
        Game game = controller.getGame();
        if(game != null)
        {
            int id = game.getId();
            boolean selected = false;
            for(int i = 0;i < vector.size();i++)
            {
                if(vector.get(i).getId() == id)
                {
                    selected = true;
                    gameList.setSelectedIndex(i);
                    break;
                }
            }
            
            if(selected == false)
            {
                controller.setGame(null);
            }
        }

        this.refreshButtons();
    }
    
    private void refreshButtons()
    {
        Game game = controller.getGame();
        if(game == null)
        {
            // No game selected.
            joinButton.setVisible(false);
            giveUpButton.setVisible(false);
            return;
        }
        
        int userId = controller.getUser().getId();
        User player1 = game.getPlayer1();
        User player2 = game.getPlayer2();
        if((player1 != null&& player1.getId() == userId)
                || (player2 != null&& player2.getId() == userId))
        {
            // Already in the game.
            joinButton.setVisible(false);
            giveUpButton.setVisible(true);
            return;
        }
        
        // Not in the game.
        joinButton.setVisible(player1 == null|| player2 == null);
        giveUpButton.setVisible(false);
    }
    
    private void onSelection(int index)
    {
        ListModel<Game> model = gameList.getModel();
        if(index >= model.getSize())
        {
            return;
        }
        
        Game game = model.getElementAt(index);
        controller.setGame(game);
    }

    private void createGame(String name)
    {
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName(name);
        
        CreateGameResponse response = controller.sendRequest(request);
        Game game = response.getGame();
        controller.setGame(game);
    }
    
    private void quitGame()
    {
        QuitGameRequest request = new QuitGameRequest();
        request.setGameId(controller.getGame().getId());
        controller.sendRequest(request);
    }
    
    private void joinGame()
    {
        Game game = controller.getGame();
        if(game == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a game first.");
            return;
        }
        
        JoinGameRequest request = new JoinGameRequest();
        request.setGameId(game.getId());

        JoinGameResponse response = controller.sendRequest(request);
        String errorMessage = response.getErrorMessage();
        if(errorMessage != null)
        {
            JOptionPane.showMessageDialog(this, errorMessage);
        }
    }
}
