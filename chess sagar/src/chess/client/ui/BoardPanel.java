package chess.client.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.IConstants;
import chess.IOTool;
import chess.client.control.Controller;
import chess.model.Bishop;
import chess.model.ChessBoard;
import chess.model.IChessMan;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.Queen;
import chess.model.Rook;
import chess.protocol.board.ProtocolChessBoard;
import chess.protocol.game.Game;
import chess.protocol.game.play.CastleRequest;
import chess.protocol.game.play.ChessManMoveRequest;
import chess.protocol.game.play.ChessManMoveResponse;
import chess.protocol.game.play.PawnPromotionRequest;
import chess.protocol.user.User;

/**
 * 
 * This panel is responsible for drawing the chess board and corresponding moves, hints, etc.
 *
 */
public class BoardPanel extends JPanel
{
    private final Controller controller;
    private Point selectedMyPosition;
    private ChessBoard board;
    
    public BoardPanel(Controller controller)
    {
        this.controller = controller;
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                onClick(e.getX(), e.getY());
            }
        });
    }
    
    private void onClick(int x, int y)
    {
        int width = getWidth();
        int height = getHeight();
        int column = x * IConstants.BOARD_SIZE / width;
        int row = y * IConstants.BOARD_SIZE / height;
        
        Point myTarget = new Point(column, row);
        if(selectedMyPosition != null&& board != null)
        {
            Point selectedPosition = this.counterPositionIfNecessary(selectedMyPosition);
            IChessMan selectedMan = board.getMatrix()[selectedPosition.x][selectedPosition.y];
            Point target = this.counterPositionIfNecessary(myTarget);
            if(selectedMan != null&& selectedMan.getDirection() == this.getMyDirection()
                    && selectedMan.getPossibleMoves().contains(target))
            {
                this.move(selectedMan, target);
                return;
            }
        }
        
        selectedMyPosition = myTarget;
        this.repaint();
    }
    
    private void move(IChessMan man, Point to)
    {
        IChessMan toMan = board.getMatrix()[to.x][to.y];
        ChessManMoveRequest request;
        if(man instanceof Pawn&& (to.y == 0|| to.y == IConstants.BOARD_SIZE - 1))
        {
            // Pawn promotion.
            PawnPromotionRequest promotionRequest = new PawnPromotionRequest();
            request = promotionRequest;
            
            String[] options = new String[] {"Queen", "Bishop", "Rook", "Knight"};
            int option = JOptionPane.showOptionDialog(this, "Select promotion type:", "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
            switch(option)
            {
            case 0: promotionRequest.setPromotionType(Queen.class); break;
            case 1: promotionRequest.setPromotionType(Bishop.class); break;
            case 2: promotionRequest.setPromotionType(Rook.class); break;
            case 3: promotionRequest.setPromotionType(Knight.class); break;
            }
        }else if(toMan != null&& toMan.getDirection() == man.getDirection())
        {
            // Castling.
            request = new CastleRequest();
        }else
        {
            // Normal move.
            request = new ChessManMoveRequest();
        }
        request.setGameId(controller.getGame().getId());
        request.setFrom(man.getPosition());
        request.setTo(to);
        ChessManMoveResponse response = controller.sendRequest(request);
        ProtocolChessBoard board = response.getBoard();
        if(board != null)
        {
            this.refresh(IOTool.toModel(board));
        }
    }
    
    public void refresh(ChessBoard board)
    {
        this.board = board;
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        
        // Draw the background.
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        
        if(board != null)
        {
            double cellWidth = width / (float) IConstants.BOARD_SIZE;
            double cellHeight = height / (float) IConstants.BOARD_SIZE;
            
            // Draw the board.
            for(int x = 0;x < IConstants.BOARD_SIZE;x++)
            {
                for(int y = 0;y < IConstants.BOARD_SIZE;y++)
                {
                    boolean isLight = ((x + y)%2 == 0);
                    graphics.setColor(isLight ? IConstants.LIGHT_COLOR : IConstants.DARK_COLOR);
                    graphics.fillRect((int) (x * cellWidth), (int) (y * cellHeight), (int) cellWidth, (int) cellHeight);
                }
            }

            // Draw the chess men.
            List<IChessMan> mans = board.getChessMans();
            for(IChessMan man : mans)
            {
                Point position = this.counterPositionIfNecessary(man.getPosition());
                int x = (int) (position.x * cellWidth);
                int y = (int) (position.y * cellHeight);
                
                boolean isBlack = (man.getDirection() == IConstants.BLACK);
                String path = (isBlack ? "black" : "white") + "_" + man.getClass().getSimpleName().toLowerCase() + ".png";
                Image manImage = ImageManager.getImage(path);
                graphics.drawImage(manImage, x, y, (int) cellWidth, (int) cellHeight, this);
            }
            
            if(selectedMyPosition != null)
            {
                // Draw the selected position.
                graphics.setStroke(new BasicStroke(3));
                graphics.setColor(Color.RED);
                this.drawRect(graphics, selectedMyPosition.x * cellWidth, selectedMyPosition.y * cellHeight, cellWidth, cellHeight);
                
                Point selectedPosition = this.counterPositionIfNecessary(selectedMyPosition);
                IChessMan selectedMan = board.getMatrix()[selectedPosition.x][selectedPosition.y];
                if(selectedMan != null)
                {
                    // Draw the possible moves.
                    graphics.setColor(Color.GREEN);
                    List<Point> possibleMoves = selectedMan.getPossibleMoves();
                    for(Point move : possibleMoves)
                    {
                        Point myMove = this.counterPositionIfNecessary(move);
                        this.drawRect(graphics, myMove.x * cellWidth, myMove.y * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
        
        g.drawImage(image, 0, 0, this);
    }
    
    private void drawRect(Graphics graphics, double x, double y, double width, double height)
    {
        graphics.drawRect((int) x+1, (int) y+1, (int) width-2, (int) height-2);
    }
    
    private Point counterPositionIfNecessary(Point position)
    {
        if(this.getMyDirection() == IConstants.WHITE)
        {
            return position;
        }else
        {
            return new Point(IConstants.BOARD_SIZE - 1 - position.x, IConstants.BOARD_SIZE - 1 - position.y);
        }
    }
    
    private int getMyDirection()
    {
        Game game = controller.getGame();
        if(game == null)
        {
            return 0;
        }
        
        int userId = controller.getUser().getId();
        User player1 = game.getPlayer1();
        if(player1 != null&& player1.getId() == userId)
        {
            return IConstants.WHITE;
        }
        User player2 = game.getPlayer2();
        if(player2 != null&& player2.getId() == userId)
        {
            return IConstants.BLACK;
        }
        return 0;
    }
}
