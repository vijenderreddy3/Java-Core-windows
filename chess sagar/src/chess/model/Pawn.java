package chess.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import chess.ChessTool;

/**
 * 
 * An instance of this class represents a pawn.
 *
 */
public class Pawn implements IChessMan
{
    private final ChessBoard board;
    private final int direction;
    private final Point position = new Point();
    private int jumpTurn;
    
    public Pawn(ChessBoard board, int direction)
    {
        this.board = board;
        this.direction = direction;
    }

    public int getJumpTurn()
    {
        return jumpTurn;
    }

    public void setJumpTurn(int jumpTurn)
    {
        this.jumpTurn = jumpTurn;
    }

    @Override
    public final ChessBoard getBoard()
    {
        return board;
    }

    @Override
    public final int getDirection()
    {
        return direction;
    }

    @Override
    public final Point getPosition()
    {
        return position;
    }

    @Override
    public final boolean move(Point to)
    {
        ChessBoard board = this.getBoard();
        
        // Check if it's my turn.
        if(this.getDirection() != board.getDirection())
        {
            return false;
        }
        
        // Check if the move is legal.
        if(!this.getPossibleMoves().contains(to))
        {
            return false;
        }

        // Remove the occupant if exists.
        IChessMan[][] mans = board.getMatrix();
        IChessMan occupant = mans[to.x][to.y];
        Point from = this.getPosition();
        if(occupant == null)
        {
            // Handle en passant.
            if(to.x != from.x)
            {
                board.getChessMans().remove(mans[to.x][from.y]);
            }
        }else
        {
            board.getChessMans().remove(occupant);
        }

        // Record jump turn.
        if(Math.abs(to.y - from.y) == 2)
        {
            jumpTurn = board.getTurn();
        }
        
        // Move to the new position.
        from.setLocation(to);
        
        board.updateMatrix();
        board.setTurn(board.getTurn() + 1);
        board.setDirection(-board.getDirection());
        
        
        return true;
    }

    @Override
    public List<Point> getPossibleMoves()
    {
        List<Point> moves = new LinkedList<>();
        Point position = this.getPosition();
        int x = position.x;
        int y = position.y;
        int direction = this.getDirection();
        
        // Upper left capture.
        if(ChessTool.checkMoveOrCapture(this, x - direction, y + direction) == MoveType.CAPTURE)
        {
            moves.add(new Point(x - direction, y + direction));
        }
        
        // Upper right capture.
        if(ChessTool.checkMoveOrCapture(this, x + direction, y + direction) == MoveType.CAPTURE)
        {
            moves.add(new Point(x + direction, y + direction));
        }
        
        // Straight move.
        if(ChessTool.checkMoveOrCapture(this, x, y + direction) == MoveType.MOVE)
        {
            moves.add(new Point(x, y + direction));
            
            // Initial jump.
            if(((direction == 1&& y == 1)|| (direction == -1&& y == 6))
                    && ChessTool.checkMoveOrCapture(this, x, y + 2*direction) == MoveType.MOVE)
            {
                moves.add(new Point(x, y + 2*direction));
            }
        }

        // En passant captures.
        ChessBoard board = this.getBoard();
        int turn = board.getTurn();
        IChessMan[][] mans = board.getMatrix();
        // Left.
        if(ChessTool.checkMoveOrCapture(this, x - 1, y) == MoveType.CAPTURE)
        {
            IChessMan toCapture = mans[x - 1][y];
            if(toCapture instanceof Pawn&& ((Pawn) toCapture).jumpTurn + 1 == turn)
            {
                moves.add(new Point(x - 1, y + 1));
            }
        }
        // Right.
        if(ChessTool.checkMoveOrCapture(this, x + 1, y) == MoveType.CAPTURE)
        {
            IChessMan toCapture = mans[x + 1][y];
            if(toCapture instanceof Pawn&& ((Pawn) toCapture).jumpTurn + 1 == turn)
            {
                moves.add(new Point(x + 1, y + 1));
            }
        }
        
        return moves;
    }

    public boolean moveAndPromote(Point to, Class<? extends IChessMan> promotionType)
    {
        ChessBoard board = this.getBoard();
        
        // Check if it's my turn.
        if(this.getDirection() != board.getDirection())
        {
            return false;
        }
        
        // Check if the move is legal.
        if((to.y != 0&& to.y != 7)|| !this.getPossibleMoves().contains(to))
        {
            return false;
        }
        
        // Check if the promotion is legal.
        int direction = this.getDirection();
        IChessMan promotion;
        if(promotionType == Bishop.class)
        {
            promotion = new Bishop(board, direction);
        }else if(promotionType == Knight.class)
        {
            promotion = new Knight(board, direction);
        }else if(promotionType == Queen.class)
        {
            promotion = new Queen(board, direction);
        }else if(promotionType == Rook.class)
        {
            promotion = new Rook(board, direction);
        }else
        {
            return false;
        }
        promotion.getPosition().setLocation(to);

        // Remove the occupant if exists.
        IChessMan[][] matrix = board.getMatrix();
        IChessMan occupant = matrix[to.x][to.y];
        List<IChessMan> mans = board.getChessMans();
        if(occupant != null)
        {
            mans.remove(occupant);
            matrix[to.x][to.y] = null;
        }
        
        // Remove this pawn.
        mans.remove(this);
        Point position = this.getPosition();
        matrix[position.x][position.y] = null;
        
        // Add the promotion.
        mans.add(promotion);
        matrix[to.x][to.y] = promotion;
        
        board.updateMatrix();
        board.setTurn(board.getTurn() + 1);
        board.setDirection(-board.getDirection());
        
        return true;
    }
}
