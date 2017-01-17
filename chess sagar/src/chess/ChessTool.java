package chess;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.model.Bishop;
import chess.model.ChessBoard;
import chess.model.ICastleable;
import chess.model.IChessMan;
import chess.model.King;
import chess.model.Knight;
import chess.model.MoveType;
import chess.model.Pawn;
import chess.model.Queen;
import chess.model.Rook;

/**
 * 
 * A tool for chess related operations.
 *
 */
public class ChessTool
{
    public ChessTool()
    {
        
    }
    
    public static boolean isGameOver(ChessBoard board)
    {
        boolean hasKing = false;
        for(IChessMan man : board.getChessMans())
        {
            if(man instanceof King)
            {
                if(hasKing)
                {
                    return false;
                }else
                {
                    hasKing = true;
                }
            }
        }
        return true;
    }
    
    public static ChessBoard newBoard()
    {
        ChessBoard board = new ChessBoard();
        List<IChessMan> mans = board.getChessMans();
        
        // White.
        mans.add(position(new Rook(board, 1), 0, 0));
        mans.add(position(new Rook(board, 1), 7, 0));
        mans.add(position(new Knight(board, 1), 1, 0));
        mans.add(position(new Knight(board, 1), 6, 0));
        mans.add(position(new Bishop(board, 1), 2, 0));
        mans.add(position(new Bishop(board, 1), 5, 0));
        mans.add(position(new Queen(board, 1), 3, 0));
        mans.add(position(new King(board, 1), 4, 0));
        for(int i = 0;i < 8;i++)
        {
            mans.add(position(new Pawn(board, 1), i, 1));
        }
        
        // Black.
        mans.add(position(new Rook(board, -1), 0, 7));
        mans.add(position(new Rook(board, -1), 7, 7));
        mans.add(position(new Knight(board, -1), 1, 7));
        mans.add(position(new Knight(board, -1), 6, 7));
        mans.add(position(new Bishop(board, -1), 2, 7));
        mans.add(position(new Bishop(board, -1), 5, 7));
        mans.add(position(new Queen(board, -1), 3, 7));
        mans.add(position(new King(board, -1), 4, 7));
        for(int i = 0;i < 8;i++)
        {
            mans.add(position(new Pawn(board, -1), i, 6));
        }
        
        board.setDirection(IConstants.WHITE);
        board.setTurn(1);
        board.updateMatrix();
        return board;
    }
    
    public static IChessMan position(IChessMan man, int x, int y)
    {
        man.getPosition().setLocation(x, y);
        return man;
    }
    
    public static MoveType moveOrCapture(IChessMan man, List<Point> moves, int xp, int yp)
    {
        Point position = man.getPosition();
        int x = position.x + xp;
        int y = position.y + yp;
        MoveType type = checkMoveOrCapture(man, x, y);
        if(type != null)
        {
            moves.add(new Point(x, y));
        }
        return type;
    }
    
    public static MoveType checkMoveOrCapture(IChessMan man, int x, int y)
    {
        if(x >= 0&& x < IConstants.BOARD_SIZE
                && y >= 0&& y < IConstants.BOARD_SIZE)
        {
            IChessMan occupant = man.getBoard().getMatrix()[x][y];
            if(occupant == null)
            {
                return MoveType.MOVE;
            }else if(occupant.getDirection() != man.getDirection())
            {
                return MoveType.CAPTURE;
            }
        }
        return null;
    }
    
    public static List<Point> checkCastling(King king)
    {
        List<Point> castlings = new LinkedList<>();
        if(king.isMoved())
        {
            return castlings;
        }
        
        int direction = king.getDirection();
        List<IChessMan> mans = king.getBoard().getChessMans();
        for(IChessMan man : mans)
        {
            if(man.getDirection() == direction&& man instanceof Rook&& canCastle(king, (Rook) man))
            {
                castlings.add(man.getPosition());
            }
        }
        
        return castlings;
    }
    
    public static King checkCastling(Rook rook)
    {
        if(rook.isMoved())
        {
            return null;
        }
        
        int direction = rook.getDirection();
        List<IChessMan> mans = rook.getBoard().getChessMans();
        King king = null;
        for(IChessMan man : mans)
        {
            if(man.getDirection() == direction&& man instanceof King)
            {
                king = (King) man;
                break;
            }
        }
        
        if(canCastle(king, rook))
        {
            return king;
        }
        return null;
    }
    
    public static boolean canCastle(King king, Rook rook)
    {
        if(king == null|| rook == null|| king.isMoved()|| rook.isMoved()
                || king.getDirection() != rook.getDirection())
        {
            return false;
        }
        Point kingPosition = king.getPosition();
        Point rookPosition = rook.getPosition();
        if(kingPosition.y != rookPosition.y)
        {
            // This rook was promoted from a pawn.
            return false;
        }
        
        ChessBoard board = king.getBoard();
        int y = kingPosition.y;
        int kingX = kingPosition.x;
        int rookX = rookPosition.x;
        int startX;
        int endX;
        int rookMiddleX;
        if(Math.abs(kingX - rookX) == 3)
        {
            rookMiddleX = -1;
            startX = kingX;
            endX = kingX + 2;
        }else
        {
            rookMiddleX = (rookX*3 + kingX)/4;
            startX = kingX - 2;
            endX = kingX;
        }
        
        // Check rook middle.
        if(rookMiddleX > 0&& board.getMatrix()[rookMiddleX][y] != null)
        {
            // The rook passage is being blocked.
            return false;
        }
        
        // Check king passage.
        int direction = king.getDirection();
        for(IChessMan man : board.getChessMans())
        {
            if(man == king)
            {
                continue;
            }
            
            // Check blocks.
            Point position = man.getPosition();
            if(position.y == y&& position.x >= startX&& position.x <= endX)
            {
                // The king passage is being blocked.
                return false;
            }
            
            // Check attacks.
            if(man.getDirection() == direction)
            {
                continue;
            }
            List<Point> moves = (man instanceof ICastleable ? 
                    ((ICastleable) man).getPossibleMoves(false) : man.getPossibleMoves());
            for(Point move : moves)
            {
                if(move.y == y&& move.x >= startX&& move.x <= endX)
                {
                    // The king passage is being attacked.
                    return false;
                }
            }
        }
        
        return true;
    }
}
