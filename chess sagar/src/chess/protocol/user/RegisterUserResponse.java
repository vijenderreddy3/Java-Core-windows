package chess.protocol.user;

import chess.protocol.Response;

/**
 * 
 * An instance of this class represents a response of registering a new user.
 *
 */
public class RegisterUserResponse extends Response
{
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
