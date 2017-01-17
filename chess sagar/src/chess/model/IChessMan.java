package chess.model;

import java.awt.Point;
import java.util.List;

/**
 * 
 * An interface defining the behavior of a chess man.
 *
 */
public interface IChessMan
{
    ChessBoard getBoard();
    
    int getDirection();
    
    Point getPosition();
    
    boolean move(Point to);
    
    List<Point> getPossibleMoves();
}
