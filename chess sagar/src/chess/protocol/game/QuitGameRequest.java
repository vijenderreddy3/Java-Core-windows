package chess.protocol.game;

import chess.protocol.Request;

/**
 * 
 * An instance of this class represents a request of quitting a game.
 *
 */
public class QuitGameRequest extends Request
{
    private int gameId;

    public int getGameId()
    {
        return gameId;
    }

    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }
}
