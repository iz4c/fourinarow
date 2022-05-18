import online.fourinarow.explorer.StateExplorer;
import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test board sizes to their completion based on Tromp's data
 */
public class BoardSizeTest {

    @Test
    public void boardSizeTest() {
        for (int width = 1; width <= 5; width++) {
            for (int height = 1; height <= 4; height++) {
                System.out.println("\nAnalysing board size " + width + "x" + height);

                Board board = new Board(width, height);
                StateExplorer explorer = new StateExplorer(board, Tile.X_TILE, width * height);

                System.out.println("Looked at " + explorer.getBoards().size() + " unique positions at depth " + width * height);
                long targetResult = TestData.getPermutationsForBoardSize(width, height);
                long actualResult = explorer.getBoards().size();

                assertEquals(targetResult, actualResult);
            }
        }
    }

}
