package chess.client.control;

import chess.IConstants;
import chess.IOTool;
import chess.client.ui.BoardPanel;
import chess.model.ChessBoard;
import chess.protocol.game.Game;
import chess.protocol.game.play.GetBoardRequest;
import chess.protocol.game.play.GetBoardResponse;

/**
 * 
 * This class is responsible for update the chess board.
 *
 */
public class BoardUpdater implements Runnable
{
    private final Controller controller;
    
    public BoardUpdater(Controller controller)
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
                BoardPanel panel = controller.getMainFrame().getGamePanel().getBoardPanel();
                Game game = controller.getGame();
                if(game == null)
                {
                    panel.refresh(null);
                }else
                {
                    GetBoardRequest request = new GetBoardRequest();
                    request.setGameId(game.getId());
                    
                    GetBoardResponse response = controller.sendRequest(request);
                    ChessBoard board = IOTool.toModel(response.getBoard());
                    panel.refresh(board);
                }
                
                Thread.sleep(IConstants.BOARD_REFRESH_INTERVAL);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
