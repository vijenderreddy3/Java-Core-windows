package chess.protocol.user;

import chess.protocol.Request;

/**
 * 
 * An instance of this class represents a request of registering a new user.
 *
 */
public class RegisterUserRequest extends Request
{
    private String username;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
