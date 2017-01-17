package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.ChessTool;

/**
 * 
 * An instance of this class represents a king.
 *
 */
public class King extends AChessMan implements ICastleable
{
    private boolean moved;
    
    public King(ChessBoard board, int direction)
    {
        super(board, direction);
    }
    
    @Override
    public boolean isMoved()
    {
        return moved;
    }

    @Override
    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }

    @Override
    public List<Point> getPossibleMoves()
    {
        return this.getPossibleMoves(true);
    }

    @Override
    public List<Point> getPossibleMoves(boolean checkCastling)
    {
        List<Point> moves = new LinkedList<>();
        ChessTool.moveOrCapture(this, moves, -1, -1);
        ChessTool.moveOrCapture(this, moves, -1, 0);
        ChessTool.moveOrCapture(this, moves, -1, 1);
        ChessTool.moveOrCapture(this, moves, 0, -1);
        ChessTool.moveOrCapture(this, moves, 0, 1);
        ChessTool.moveOrCapture(this, moves, 1, -1);
        ChessTool.moveOrCapture(this, moves, 1, 0);
        ChessTool.moveOrCapture(this, moves, 1, 1);
        if(checkCastling)
        {
            moves.addAll(ChessTool.checkCastling(this));
        }
        return moves;
    }

    @Override
    public boolean castle(Point to)
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
        
        Rook rook = (Rook) board.getMatrix()[to.x][to.y];
        Point rookPosition = rook.getPosition();
        Point position = this.getPosition();
        if(rookPosition.x < position.x)
        {
            rookPosition.x += 3;
            position.x -= 2;
        }else
        {
            rookPosition.x -= 2;
            position.x += 2;
        }

        board.updateMatrix();
        board.setTurn(board.getTurn() + 1);
        board.setDirection(-board.getDirection());
        
        return true;
    }
}
