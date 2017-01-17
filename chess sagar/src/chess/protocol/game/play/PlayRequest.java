package chess.protocol.game.play;

import chess.protocol.Request;

/**
 * 
 * An instance of this class represents a request of game play.
 *
 */
public class PlayRequest extends Request
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
