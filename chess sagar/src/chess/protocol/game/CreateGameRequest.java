package chess.protocol.game;

import chess.protocol.Request;

/**
 * 
 * An instance of this class represents a request of creating a new game.
 *
 */
public class CreateGameRequest extends Request
{
    private String gameName;

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }
}
