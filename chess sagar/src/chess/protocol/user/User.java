package chess.protocol.user;

import java.io.Serializable;

/**
 * 
 * An instance of this class represents a user.
 *
 */
public class User implements Serializable
{
    private int id;
    private String username;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
