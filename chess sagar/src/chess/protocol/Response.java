package chess.protocol;

import java.io.Serializable;

/**
 * 
 * An instance of this class represents a response.
 *
 */
public class Response implements Serializable
{
    private String errorMessage;

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
