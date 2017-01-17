package chess.protocol.game.play;

import chess.protocol.Response;
import chess.protocol.board.ProtocolChessBoard;

/**
 * 
 * An instance of this class represents a response of game play.
 *
 */
public class PlayResponse extends Response
{
    private ProtocolChessBoard board;

    public ProtocolChessBoard getBoard()
    {
        return board;
    }

    public void setBoard(ProtocolChessBoard board)
    {
        this.board = board;
    }
}
