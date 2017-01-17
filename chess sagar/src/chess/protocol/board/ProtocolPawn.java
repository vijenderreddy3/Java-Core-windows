package chess.protocol.board;

/**
 * 
 * An instance of this class represents a pawn for communication.
 *
 */
public class ProtocolPawn extends ProtocolChessMan
{
    private int jumpTurn;

    public int getJumpTurn()
    {
        return jumpTurn;
    }

    public void setJumpTurn(int jumpTurn)
    {
        this.jumpTurn = jumpTurn;
    }
}
