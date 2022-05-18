import online.fourinarow.explorer.StateExplorer;
import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test finds the number of legal positions up to a certain depth using StateExplorer
 * It is verified against Tromp's data, and passes all tests it has completed
 */
public class PermutationTest {

    // 11 is chosen to check games finish after a diagonal win
    public static final int DEPTH = 11;

    @Test
    public void permutationTest() {
        System.out.println("Analysing depth " + DEPTH + "...");

        Board board = new Board(7, 6);
        StateExplorer explorer = new StateExplorer(board, Tile.X_TILE, DEPTH);

        System.out.println("Looked at " + explorer.getBoards().size() + " unique positions at depth " + DEPTH);
        long targetResult = TestData.getPermutationsToDepth(DEPTH);
        long actualResult = explorer.getBoards().size();

        assertEquals(targetResult, actualResult);
    }

}
