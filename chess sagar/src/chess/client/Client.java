package chess.client;

import chess.client.control.Controller;

/**
 * 
 * Entrance of Client.
 *
 */
public class Client
{
    public static void main(String[] strings)
    {
        new Controller("localhost", 2345);
    }
}
