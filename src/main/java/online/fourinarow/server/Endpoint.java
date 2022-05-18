package online.fourinarow.server;

import online.fourinarow.agent.impl.alphabeta.MultithreadABAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;
import online.fourinarow.game.WinChecker;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Endpoint {

    @CrossOrigin
    @GetMapping(value = "/minimax/{depth}/{width}/{height}/{data}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> minimax(
            @PathVariable int depth,
            @PathVariable int width,
            @PathVariable int height,
            @PathVariable String data
    ) {
        Board board = new Board(width + "|" + height + "|" + data);
        Tile tile = board.getTurnCount() % 2 == 0 ? Tile.X_TILE : Tile.O_TILE;

        MoveResult preState = getBoardState(board, tile.getOpposite());
        if (preState != MoveResult.CONTINUE) {
            Map<String, String> response = new HashMap<>();
            response.put("column", "-1");
            response.put("result", preState.name());

            return response;
        }

        MultithreadABAgent agent = new MultithreadABAgent(depth);
        int column = agent.provideMove(board, tile);
        board.dropTile(column, tile);  // update our board for the win checker

        MoveResult result = getBoardState(board, tile);

        Map<String, String> response = new HashMap<>();
        response.put("column", Integer.toString(column));
        response.put("result", result.name());

        return response;
    }

    private MoveResult getBoardState(Board board, Tile tile) {
        MoveResult result;
        if (WinChecker.isWinning(board)) {
            if (tile == Tile.X_TILE) result = MoveResult.X_WIN;
            else result = MoveResult.O_WIN;
        }
        else if (board.isBoardFull()) result = MoveResult.DRAW;
        else result = MoveResult.CONTINUE;

        return result;
    }

}
