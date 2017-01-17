package chess.model;

import java.awt.Point;
import java.util.List;

/**
 * 
 * An interface defining the behavior of castleable chess men (rooks and kings).
 *
 */
public interface ICastleable
{
    boolean isMoved();
    
    void setMoved(boolean moved);
    
    List<Point> getPossibleMoves(boolean checkCastling);
    
    boolean castle(Point to);
}
