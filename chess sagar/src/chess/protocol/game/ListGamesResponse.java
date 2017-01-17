package chess.protocol.game;

import java.util.List;

import chess.protocol.Response;

/**
 * 
 * An instance of this class represents a request for the game list.
 *
 */
public class ListGamesResponse extends Response
{
    private List<Game> games;

    public List<Game> getGames()
    {
        return games;
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }
}
