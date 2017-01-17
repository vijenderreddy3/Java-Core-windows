package chess.protocol.board;

/**
 * 
 * An instance of this class represents a rook for communication.
 *
 */
public class ProtocolRook extends ProtocolChessMan
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
