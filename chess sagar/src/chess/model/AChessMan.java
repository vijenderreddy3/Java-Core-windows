package chess.model;

import java.awt.Point;

/**
 * 
 * An abstract implementation of chess man.
 *
 */
public abstract class AChessMan implements IChessMan
{
    private final ChessBoard board;
    private final int direction;
    private final Point position = new Point();
    
    public AChessMan(ChessBoard board, int direction)
    {
        this.board = board;
        this.direction = direction;
    }

    @Override
    public final ChessBoard getBoard()
    {
        return board;
    }

    @Override
    public final int getDirection()
    {
        return direction;
    }

    @Override
    public final Point getPosition()
    {
        return position;
    }

    @Override
    public final boolean move(Point to)
    {
        ChessBoard board = this.getBoard();
        
        // Check if it's my turn.
        if(this.getDirection() != board.getDirection())
        {
            return false;
        }
        
        // Check if the move is legal.
        if(!this.getPossibleMoves().contains(to))
        {
            return false;
        }

        // Remove the occupant if exists.
        IChessMan[][] mans = board.getMatrix();
        IChessMan occupant = mans[to.x][to.y];
        if(occupant != null)
        {
            board.getChessMans().remove(occupant);
            mans[to.x][to.y] = null;
        }
        
        // Move to the new position.
        this.getPosition().setLocation(to);
        
        board.updateMatrix();
        board.setTurn(board.getTurn() + 1);
        board.setDirection(-board.getDirection());
        
        return true;
    }
}
