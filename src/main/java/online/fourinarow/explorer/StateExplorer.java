package online.fourinarow.explorer;

import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Create a set of all legal game states to a certain depth from a starting position
 * It works recursively, and doesn't maintain a tree of the moves explored, only a set of the unique positions
 * it has visited
 */
public class StateExplorer {

    private final Board originalState;
    private final Set<String> uniqueBoards = new HashSet<>();

    public StateExplorer(Board board, Tile nextMove, int depth) {
        this.originalState = board;
        this.uniqueBoards.add(originalState.toString());
        exploreMoves(board, nextMove, depth);
    }

    private void exploreMoves(Board board, Tile nextMove, int depth) {
        if (depth == 0) return;

        // check each column for a potential move
        for (int column = 0; column < board.getWidth(); column++) {
            Board newBoard = board.cloneBoard();
            MoveResult result = newBoard.dropTile(column, nextMove);  // find the result of placing a tile in that column

            if (result != MoveResult.INVALID) {  // do nothing if it's invalid, it's not a legal move
                int newDepth = result == MoveResult.CONTINUE ? depth - 1 : 0;  // this stops us from playing another turn after a win or draw occurs
                String newBoardString = newBoard.toString();
                if (!uniqueBoards.contains(newBoardString)) {  // if we've not already found this position
                    uniqueBoards.add(newBoard.toString());  // add it to the list of visited boards
                    exploreMoves(newBoard, nextMove.getOpposite(), newDepth);  // explore its children
                }
            }
        }
    }

    public Board getOriginalState() {
        return originalState;
    }

    public Set<String> getBoards() {
        return uniqueBoards;
    }

}
