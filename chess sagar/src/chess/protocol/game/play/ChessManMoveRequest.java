package chess.protocol.game.play;

import java.awt.Point;

/**
 * 
 * An instance of this class represents a move request of a chess man.
 *
 */
public class ChessManMoveRequest extends PlayRequest
{
    private Point from;
    private Point to;

    public Point getTo()
    {
        return to;
    }

    public void setTo(Point to)
    {
        this.to = to;
    }

    public Point getFrom()
    {
        return from;
    }

    public void setFrom(Point from)
    {
        this.from = from;
    }
}
