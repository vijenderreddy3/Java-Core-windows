package chess.protocol.game;

import java.io.Serializable;

import chess.protocol.user.User;

/**
 * 
 * An instance of this class represents a game for communication.
 *
 */
public class Game implements Serializable
{
    private int id;
    private String name;
    private long lastTime;
    private User player1;
    private long player1Time;
    private User player2;
    private long player2Time;

    public long getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(long lastTime)
    {
        this.lastTime = lastTime;
    }

    public long getPlayer1Time()
    {
        return player1Time;
    }

    public void setPlayer1Time(long player1Time)
    {
        this.player1Time = player1Time;
    }

    public long getPlayer2Time()
    {
        return player2Time;
    }

    public void setPlayer2Time(long player2Time)
    {
        this.player2Time = player2Time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(User player2)
    {
        this.player2 = player2;
    }

    public User getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(User player1)
    {
        this.player1 = player1;
    }
    
    @Override
    public String toString()
    {
        String player1Name = (player1 == null ? "?" : player1.getUsername());
        String player2Name = (player2 == null ? "?" : player2.getUsername());
        return id + ": " + name + " (" 
                + player1Name + " " +  this.toString(player1Time)
                + " vs " 
                + player2Name + " " + this.toString(player2Time)
                + ")";
    }
    
    private String toString(long time)
    {
        time /= 1000;
        long seconds = time % 60;
        time /= 60;
        long minutes = time % 60;
        time /= 60;
        long hours = time;
        
        String string = this.append("", hours);
        string = this.append(string + ":", minutes);
        string = this.append(string + ":", seconds);
        return string;
    }
    
    private String append(String string, long number)
    {
        if(number < 10)
        {
            string += "0";
        }
        string += number;
        return string;
    }
}
