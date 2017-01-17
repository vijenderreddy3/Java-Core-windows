package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.IConstants;

/**
 * 
 * An instance of this class represents a chess board.
 *
 */
public class ChessBoard
{
    private int direction;
    private int turn;
    private final List<IChessMan> chessMans = new LinkedList<>();
    private IChessMan[][] matrix;

    public IChessMan[][] getMatrix()
    {
        return matrix;
    }

    public List<IChessMan> getChessMans()
    {
        return chessMans;
    }

    public int getTurn()
    {
        return turn;
    }

    public void setTurn(int turn)
    {
        this.turn = turn;
    }
    
    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public void updateMatrix()
    {
        matrix = new IChessMan[IConstants.BOARD_SIZE][IConstants.BOARD_SIZE];
        for(IChessMan man : chessMans)
        {
            Point position = man.getPosition();
            matrix[position.x][position.y] = man;
        }
    }
}
