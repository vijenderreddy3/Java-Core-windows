package chess;

import java.awt.Color;

/**
 * 
 * An interface for constants.
 *
 */
public interface IConstants
{
    int BOARD_SIZE = 8;
    
    int BLACK = 1;
    int WHITE = -BLACK;
    
    long GAMES_REFRESH_INTERVAL = 1000;
    long BOARD_REFRESH_INTERVAL = 1000;
    
    Color LIGHT_COLOR = new Color(209, 139, 71);
    Color DARK_COLOR = new Color(255, 206, 158);
}
