package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.ChessTool;

/**
 * 
 * An instance of this class represents a knight.
 *
 */
public class Knight extends AChessMan
{
    public Knight(ChessBoard board, int direction)
    {
        super(board, direction);
    }
    
    @Override
    public List<Point> getPossibleMoves()
    {
        List<Point> moves = new LinkedList<>();
        ChessTool.moveOrCapture(this, moves, 1, 2);
        ChessTool.moveOrCapture(this, moves, -1, 2);
        ChessTool.moveOrCapture(this, moves, 1, -2);
        ChessTool.moveOrCapture(this, moves, -1, -2);
        ChessTool.moveOrCapture(this, moves, 2, 1);
        ChessTool.moveOrCapture(this, moves, -2, 1);
        ChessTool.moveOrCapture(this, moves, 2, -1);
        ChessTool.moveOrCapture(this, moves, -2, -1);
        return moves;
    }
}
