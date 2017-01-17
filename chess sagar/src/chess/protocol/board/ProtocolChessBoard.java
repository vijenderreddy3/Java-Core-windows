package chess.protocol.board;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * An instance of this class represents a chess board for communication.
 *
 */
public class ProtocolChessBoard implements Serializable
{
    private int direction;
    private int turn;
    private List<ProtocolChessMan> chessMans;

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public int getTurn()
    {
        return turn;
    }

    public void setTurn(int turn)
    {
        this.turn = turn;
    }

    public List<ProtocolChessMan> getChessMans()
    {
        return chessMans;
    }

    public void setChessMans(List<ProtocolChessMan> chessMans)
    {
        this.chessMans = chessMans;
    }
}
