package chess.client.control;

import java.util.Vector;

import chess.IConstants;
import chess.protocol.game.Game;
import chess.protocol.game.ListGamesRequest;
import chess.protocol.game.ListGamesResponse;

/**
 * 
 * This class is responsible for updating the game list.
 *
 */
public class GamesUpdater implements Runnable
{
    private final Controller controller;
    
    public GamesUpdater(Controller controller)
    {
        this.controller = controller;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                ListGamesRequest request = new ListGamesRequest();
                ListGamesResponse response = controller.sendRequest(request);
                Vector<Game> games = new Vector<>(response.getGames());
                controller.getMainFrame().getGamePanel().refreshGames(games);
                
                Thread.sleep(IConstants.GAMES_REFRESH_INTERVAL);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
