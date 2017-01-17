package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.ChessTool;

/**
 * 
 * An instance of this class represents a rook.
 *
 */
public class Rook extends AChessMan implements ICastleable
{
    private boolean moved;
    
    public Rook(ChessBoard board, int direction)
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
        this.addPossibleMoves(moves, -1, 0);
        this.addPossibleMoves(moves, 1, 0);
        this.addPossibleMoves(moves, 0, -1);
        this.addPossibleMoves(moves, 0, 1);
        if(checkCastling)
        {
            King king = ChessTool.checkCastling(this);
            if(king != null)
            {
                moves.add(king.getPosition());
            }
        }
        return moves;
    }

    private void addPossibleMoves(List<Point> moves, int xp, int yp)
    {
        for(int xpp = xp, ypp = yp;;xpp += xp, ypp += yp)
        {
            MoveType type = ChessTool.moveOrCapture(this, moves, xpp, ypp);
            if(type != MoveType.MOVE)
            {
                break;
            }
        }
    }

    @Override
    public boolean castle(Point to)
    {
        ChessBoard board = this.getBoard();
        King king = (King) board.getMatrix()[to.x][to.y];
        king.castle(this.getPosition());
        return true;
    }
}
