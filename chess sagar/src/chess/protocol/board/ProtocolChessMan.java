package chess.protocol.board;

import java.awt.Point;
import java.io.Serializable;
/**
 * 
 * An instance of this class represents a chess man for communication.
 *
 */
public class ProtocolChessMan implements Serializable
{
    private int direction;
    private Point position;

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }
}
