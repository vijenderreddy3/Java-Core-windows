package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.ChessTool;

/**
 * 
 * An instance of this class represents a bishop.
 *
 */
public class Bishop extends AChessMan
{
    public Bishop(ChessBoard board, int direction)
    {
        super(board, direction);
    }

    @Override
    public List<Point> getPossibleMoves()
    {
        List<Point> moves = new LinkedList<>();
        this.addPossibleMoves(moves, -1, -1);
        this.addPossibleMoves(moves, 1, -1);
        this.addPossibleMoves(moves, -1, 1);
        this.addPossibleMoves(moves, 1, 1);
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
}
