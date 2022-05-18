package online.fourinarow.agent.impl.alphabeta;

import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class AlphaBetaState {

    private final Board board;
    private final Tile myTile;
    private final int lastColumnPlayed;
    private final Tile nextTile;
    private final int searchDepth;
    private final MoveResult gameState;

    public AlphaBetaState(Board board, Tile myTile, Tile nextTile, int searchDepth, MoveResult result) {
        this.board = board;
        this.myTile = myTile;
        this.lastColumnPlayed = -1;
        this.nextTile = nextTile;
        this.searchDepth = searchDepth;
        this.gameState = result;
    }

    public AlphaBetaState(Board board, Tile myTile, int lastColumnPlayed, Tile nextTile, int searchDepth, MoveResult result) {
        this.board = board;
        this.myTile = myTile;
        this.lastColumnPlayed = lastColumnPlayed;
        this.nextTile = nextTile;
        this.searchDepth = searchDepth;
        this.gameState = result;
    }

    // retrieve rating by searching children
    // positive for X, negative for O
    /*public int getRating() {
        if (this.searchDepth == 0) return 0;


        // board.getChildren ??
    }*/

    public List<AlphaBetaState> getNextStates() {
        List<AlphaBetaState> nextStates = new ArrayList<>();

        if (this.searchDepth == 0) return nextStates;

        for (int column = 0; column < board.getWidth(); column++) {
            Board newBoard = board.cloneBoard();

            MoveResult result = newBoard.dropTile(column, this.nextTile);
            if (result != MoveResult.INVALID) {
                // only look deeper if the game actually continues
                int newDepth = result == MoveResult.CONTINUE ? this.searchDepth - 1 : 0;
                AlphaBetaState newState = new AlphaBetaState(newBoard, this.myTile, column, this.nextTile.getOpposite(), newDepth, result);
                nextStates.add(newState);
            }
        }

        return nextStates;
    }

    public Board getBoard() {
        return this.board;
    }

    public MoveResult getGameState() {
        return this.gameState;
    }

    public Tile getNextTile() {
        return this.nextTile;
    }

    public int getLastColumnPlayed() {
        return this.lastColumnPlayed;
    }

}
