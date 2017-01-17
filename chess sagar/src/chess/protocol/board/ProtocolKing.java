package chess.protocol.board;

/**
 * 
 * An instance of this class represents a king for communication.
 *
 */
public class ProtocolKing extends ProtocolChessMan
{
    private boolean moved;

    public boolean isMoved()
    {
        return moved;
    }

    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }
}
