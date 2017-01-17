package chess;

import java.util.LinkedList;
import java.util.List;

import chess.model.Bishop;
import chess.model.ChessBoard;
import chess.model.IChessMan;
import chess.model.King;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.Queen;
import chess.model.Rook;
import chess.protocol.board.ProtocolBishop;
import chess.protocol.board.ProtocolChessBoard;
import chess.protocol.board.ProtocolChessMan;
import chess.protocol.board.ProtocolKing;
import chess.protocol.board.ProtocolKnight;
import chess.protocol.board.ProtocolPawn;
import chess.protocol.board.ProtocolQueen;
import chess.protocol.board.ProtocolRook;

/**
 * 
 * A tool for IO and protocol related operations.
 *
 */
public class IOTool
{
    private IOTool()
    {
        
    }
    
    public static ProtocolChessBoard toProtocol(ChessBoard board)
    {
        if(board == null)
        {
            return null;
        }
        
        ProtocolChessBoard protocolChessBoard = new ProtocolChessBoard();
        protocolChessBoard.setDirection(board.getDirection());
        protocolChessBoard.setTurn(board.getTurn());
        List<ProtocolChessMan> protocolChessMans = new LinkedList<>();
        protocolChessBoard.setChessMans(protocolChessMans);
        for(IChessMan man : board.getChessMans())
        {
            protocolChessMans.add(toProtocol(man));
        }
        return protocolChessBoard;
    }
    
    public static ProtocolChessMan toProtocol(IChessMan man)
    {
        if(man == null)
        {
            return null;
        }
        
        ProtocolChessMan protocolChessMan;
        if(man instanceof Bishop)
        {
            protocolChessMan = new ProtocolBishop();
        }else if(man instanceof King)
        {
            ProtocolKing king = new ProtocolKing();
            king.setMoved(((King) man).isMoved());
            protocolChessMan = king;
        }else if(man instanceof Knight)
        {
            protocolChessMan = new ProtocolKnight();
        }else if(man instanceof Pawn)
        {
            ProtocolPawn pawn = new ProtocolPawn();
            pawn.setJumpTurn(((Pawn) man).getJumpTurn());
            protocolChessMan = pawn;
        }else if(man instanceof Queen)
        {
            protocolChessMan = new ProtocolQueen();
        }else if(man instanceof Rook)
        {
            ProtocolRook rook = new ProtocolRook();
            rook.setMoved(((Rook) man).isMoved());
            protocolChessMan = rook;
        }else
        {
            throw new RuntimeException("Unknown chess man type \"" + man.getClass() + "\".");
        }
        
        protocolChessMan.setDirection(man.getDirection());
        protocolChessMan.setPosition(man.getPosition());
        
        return protocolChessMan;
    }
    
    public static ChessBoard toModel(ProtocolChessBoard board)
    {
        if(board == null)
        {
            return null;
        }
        
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.setDirection(board.getDirection());
        chessBoard.setTurn(board.getTurn());
        List<IChessMan> chessMans = chessBoard.getChessMans();
        for(ProtocolChessMan man : board.getChessMans())
        {
            chessMans.add(toModel(chessBoard, man));
        }
        chessBoard.updateMatrix();
        return chessBoard;
    }
    
    public static IChessMan toModel(ChessBoard board, ProtocolChessMan man)
    {
        if(man == null)
        {
            return null;
        }
        
        IChessMan chessMan;
        int direction = man.getDirection();
        if(man instanceof ProtocolBishop)
        {
            chessMan = new Bishop(board, direction);
        }else if(man instanceof ProtocolKing)
        {
            King king = new King(board, direction);
            king.setMoved(((ProtocolKing) man).isMoved());
            chessMan = king;
        }else if(man instanceof ProtocolKnight)
        {
            chessMan = new Knight(board, direction);
        }else if(man instanceof ProtocolPawn)
        {
            Pawn pawn = new Pawn(board, direction);
            pawn.setJumpTurn(((ProtocolPawn) man).getJumpTurn());
            chessMan = pawn;
        }else if(man instanceof ProtocolQueen)
        {
            chessMan = new Queen(board, direction);
        }else if(man instanceof ProtocolRook)
        {
            Rook rook = new Rook(board, direction);
            rook.setMoved(((ProtocolRook) man).isMoved());
            chessMan = rook;
        }else
        {
            throw new RuntimeException("Unknown chess man type \"" + man.getClass() + "\".");
        }
        
        chessMan.getPosition().setLocation(man.getPosition());
        return chessMan;
    }
}
