package chess.protocol.game;

import chess.protocol.Response;

/**
 * 
 * An instance of this class represents a response of creating a new game.
 *
 */
public class CreateGameResponse extends Response
{
    private Game game;

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
