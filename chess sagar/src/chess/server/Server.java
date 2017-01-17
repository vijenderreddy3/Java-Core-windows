package chess.server;

import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import chess.ChessTool;
import chess.IOTool;
import chess.model.ChessBoard;
import chess.model.IChessMan;
import chess.model.King;
import chess.model.Pawn;
import chess.model.Rook;
import chess.protocol.game.CreateGameRequest;
import chess.protocol.game.CreateGameResponse;
import chess.protocol.game.Game;
import chess.protocol.game.JoinGameRequest;
import chess.protocol.game.JoinGameResponse;
import chess.protocol.game.ListGamesRequest;
import chess.protocol.game.ListGamesResponse;
import chess.protocol.game.QuitGameRequest;
import chess.protocol.game.QuitGameResponse;
import chess.protocol.game.play.CastleRequest;
import chess.protocol.game.play.CastleResponse;
import chess.protocol.game.play.ChessManMoveRequest;
import chess.protocol.game.play.ChessManMoveResponse;
import chess.protocol.game.play.GetBoardRequest;
import chess.protocol.game.play.GetBoardResponse;
import chess.protocol.game.play.PawnPromotionRequest;
import chess.protocol.game.play.PawnPromotionResponse;
import chess.protocol.user.RegisterUserRequest;
import chess.protocol.user.RegisterUserResponse;
import chess.protocol.user.User;

/**
 * 
 * This is the entrance of Server.
 *
 */
public class Server
{
    public static void main(String[] strings)
    {
        int port = 2345;
        if(strings.length > 0)
        {
            port = Integer.parseInt(strings[0]);
        }
        
        new Server(port).start();
    }
    
    private final int port;
    private final Map<Integer, User> idToUserMap = new ConcurrentHashMap<>();
    private int nextUserId = 1;
    private final Map<Integer, Game> idToGameMap = new ConcurrentHashMap<>();
    private int nextGameId = 1;
    private final Map<Integer, ChessBoard> gameIdToBoardMap = new ConcurrentHashMap<>();
    
    public Server(int port)
    {
        this.port = port;
    }
    
    public void start()
    {
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is up on " + port + " ...");
            while(true)
            {
                Socket socket = serverSocket.accept();
                new Thread(() -> handle(socket)).start();
            }
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private void handle(Socket socket)
    {
        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            
            Object request = inputStream.readObject();
            Object response;
            if(request instanceof RegisterUserRequest)
            {
                response = this.register((RegisterUserRequest) request);
            }else if(request instanceof CreateGameRequest)
            {
                response = this.create((CreateGameRequest) request);
            }else if(request instanceof JoinGameRequest)
            {
                response = this.join((JoinGameRequest) request);
            }else if(request instanceof ListGamesRequest)
            {
                response = this.list((ListGamesRequest) request);
            }else if(request instanceof GetBoardRequest)
            {
                response = this.get((GetBoardRequest) request);
            }else if(request instanceof PawnPromotionRequest)
            {
                response = this.promote((PawnPromotionRequest) request);
            }else if(request instanceof CastleRequest)
            {
                response = this.castle((CastleRequest) request);
            }else if(request instanceof ChessManMoveRequest)
            {
                response = this.move((ChessManMoveRequest) request);
            }else if(request instanceof QuitGameRequest)
            {
                response = this.quit((QuitGameRequest) request);
            }else
            {
                throw new RuntimeException("Unknown request type \"" + request.getClass() + "\".");
            }
            
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private RegisterUserResponse register(RegisterUserRequest request)
    {
        RegisterUserResponse response = new RegisterUserResponse();
        
        User user = new User();
        user.setId(nextUserId);
        nextUserId++;
        user.setUsername(request.getUsername());
        idToUserMap.put(user.getId(), user);
        
        response.setUser(user);
        return response;
    }
    
    private CreateGameResponse create(CreateGameRequest request)
    {
        CreateGameResponse response = new CreateGameResponse();
        
        User player1 = idToUserMap.get(request.getUserId());
        if(player1 == null)
        {
            response.setErrorMessage("User doesn't exist.");
        }else
        {
            ChessBoard board = ChessTool.newBoard();
            gameIdToBoardMap.put(nextGameId, board);
            
            Game game = new Game();
            response.setGame(game);
            game.setId(nextGameId);
            nextGameId++;
            game.setName(request.getGameName());
            game.setPlayer1(player1);
            idToGameMap.put(game.getId(), game);
        }
        
        return response;
    }
    
    private ListGamesResponse list(ListGamesRequest request)
    {
        ListGamesResponse response = new ListGamesResponse();
        
        List<Game> games = new ArrayList<>(idToGameMap.values());
        response.setGames(games);
        
        return response;
    }
    
    private JoinGameResponse join(JoinGameRequest request)
    {
        JoinGameResponse response = new JoinGameResponse();
        
        User user = idToUserMap.get(request.getUserId());
        if(user == null)
        {
            response.setErrorMessage("User doesn't exist.");
            return response;
        }
        
        Game game = idToGameMap.get(request.getGameId());
        if(game == null)
        {
            response.setErrorMessage("Game doesn't exist.");
            return response;
        }

        User player1 = game.getPlayer1();
        User player2 = game.getPlayer2();
        if(player1 == user|| player2 == user)
        {
            response.setErrorMessage("User already playing the game.");
        }else if(player1 == null)
        {
            game.setPlayer1(user);
        }else if(player2 == null)
        {
            game.setPlayer2(user);
        }else
        {
            response.setErrorMessage("No seat.");
        }
        
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        if(game.getLastTime() == 0&& player1 != null&& player2 != null)
        {
            game.setLastTime(System.currentTimeMillis());
            if(Math.random() < .5)
            {
                game.setPlayer1(player2);
                game.setPlayer2(player1);
            }
        }
        
        return response;
    }
    
    private QuitGameResponse quit(QuitGameRequest request)
    {
        QuitGameResponse response = new QuitGameResponse();
        
        User user = idToUserMap.get(request.getUserId());
        if(user == null)
        {
            response.setErrorMessage("User doesn't exist.");
            return response;
        }
        
        int gameId = request.getGameId();
        Game game = idToGameMap.get(gameId);
        if(game == null)
        {
            response.setErrorMessage("Game doesn't exist.");
            return response;
        }

        User player1 = game.getPlayer1();
        User player2 = game.getPlayer2();
        if(player1 == user)
        {
            game.setPlayer1(null);
        }else if(player2 == user)
        {
            game.setPlayer2(null);
        }else
        {
            response.setErrorMessage("You're not in the game.");
        }
        
        // Remove the game if there's no player.
        if(game.getPlayer1() == null&& game.getPlayer2() == null)
        {
            idToGameMap.remove(gameId);
            gameIdToBoardMap.remove(gameId);
        }
        
        return response;
    }
    
    private GetBoardResponse get(GetBoardRequest request)
    {
        GetBoardResponse response = new GetBoardResponse();
        
        ChessBoard board = gameIdToBoardMap.get(request.getGameId());
        if(board == null)
        {
            response.setErrorMessage("Game doesn't exist.");
        }else
        {
            response.setBoard(IOTool.toProtocol(board));
        }
        
        return response;
    }
    
    private ChessManMoveResponse move(ChessManMoveRequest request)
    {
        ChessManMoveResponse response = new ChessManMoveResponse();
        
        ChessBoard board = gameIdToBoardMap.get(request.getGameId());
        if(board == null|| ChessTool.isGameOver(board))
        {
            response.setErrorMessage("Game doesn't exist.");
            return response;
        }
        
        Point from = request.getFrom();
        Point to = request.getTo();
        IChessMan man = board.getMatrix()[from.x][from.y];
        if(man.move(to))
        {
            response.setBoard(IOTool.toProtocol(board));
            this.updateTime(request);
        }else
        {
            response.setErrorMessage("Illegal move.");
        }
        
        return response;
    }
    
    private PawnPromotionResponse promote(PawnPromotionRequest request)
    {
        PawnPromotionResponse response = new PawnPromotionResponse();
        
        ChessBoard board = gameIdToBoardMap.get(request.getGameId());
        if(board == null|| ChessTool.isGameOver(board))
        {
            response.setErrorMessage("Game doesn't exist.");
            return response;
        }
        
        Point from = request.getFrom();
        Point to = request.getTo();
        IChessMan man = board.getMatrix()[from.x][from.y];
        if(man instanceof Pawn&& ((Pawn) man).moveAndPromote(to, request.getPromotionType()))
        {
            response.setBoard(IOTool.toProtocol(board));
            this.updateTime(request);
        }else
        {
            response.setErrorMessage("Illegal move.");
        }
        
        return response;
    }
    
    private CastleResponse castle(CastleRequest request)
    {
        CastleResponse response = new CastleResponse();
        
        ChessBoard board = gameIdToBoardMap.get(request.getGameId());
        if(board == null|| ChessTool.isGameOver(board))
        {
            response.setErrorMessage("Game doesn't exist.");
            return response;
        }
        
        Point from = request.getFrom();
        Point to = request.getTo();
        IChessMan man = board.getMatrix()[from.x][from.y];
        if((man instanceof Rook&& ((Rook) man).castle(to))
                || (man instanceof King&& ((King) man).castle(to)))
        {
            response.setBoard(IOTool.toProtocol(board));
            this.updateTime(request);
        }else
        {
            response.setErrorMessage("Illegal move.");
        }
        
        return response;
    }
    
    private void updateTime(ChessManMoveRequest request)
    {
        Game game = idToGameMap.get(request.getGameId());
        long now = System.currentTimeMillis();
        long time = now - game.getLastTime();
        game.setLastTime(now);
        if(game.getPlayer1().getId() == request.getUserId())
        {
            game.setPlayer1Time(game.getPlayer1Time() + time);
        }else
        {
            game.setPlayer2Time(game.getPlayer2Time() + time);
        }
    }
}
