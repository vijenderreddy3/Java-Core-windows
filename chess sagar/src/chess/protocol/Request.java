package chess.protocol;

import java.io.Serializable;

/**
 * 
 * An instance of this class represents a request.
 *
 */
public class Request implements Serializable
{
    private int userId;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
}
