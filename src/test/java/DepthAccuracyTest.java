import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;
import online.fourinarow.agent.impl.alphabeta.AlphaBetaAgent;

public class DepthAccuracyTest {

    /*  | - - - - - - - |
        | - - - - - - - |
        | - - - - - - - |
        | - - - X - - - |
        | - - - X - - - |
        | O O O X - - - |
        ----------------- */
    private static final Board WIN_IN_ONE = new Board("7|6|-----O-----O-----O---XXX------------------");

    /*  | - - - - - - - |
        | - - - - - - - |
        | - - - - - - - |
        | - - - O - - - |
        | - - - O - - - |
        | X X X O - - - |
        ----------------- */
    private static final Board BLOCK_WIN_IN_ONE = new Board("7|6|-----X-----X-----X---OOO------------------");

    /*  | - - - - - - - |
        | - - - - - - - |
        | - - - - - - - |
        | - - - - - - - |
        | - - - - X - - |
        | X - - O O - - |
        ----------------- */
    private static final Board HORIZONTAL_THREE = new Board("7|6|-----X-----------------O----XO------------");

    public static void main(String[] args) {
        AlphaBetaAgent agent = new AlphaBetaAgent(1);
        System.out.println(WIN_IN_ONE.getPrettyPrint());
        System.out.println(agent.provideMove(WIN_IN_ONE, Tile.X_TILE));

        agent = new AlphaBetaAgent(1);
        System.out.println(BLOCK_WIN_IN_ONE.getPrettyPrint());
        System.out.println(agent.provideMove(BLOCK_WIN_IN_ONE, Tile.X_TILE));
        agent = new AlphaBetaAgent(2);
        System.out.println(agent.provideMove(BLOCK_WIN_IN_ONE, Tile.X_TILE));

        agent = new AlphaBetaAgent(1);
        System.out.println(HORIZONTAL_THREE.getPrettyPrint());
        System.out.println(agent.provideMove(HORIZONTAL_THREE, Tile.X_TILE));
        agent = new AlphaBetaAgent(2);
        System.out.println(agent.provideMove(HORIZONTAL_THREE, Tile.X_TILE));
        agent = new AlphaBetaAgent(3);
        System.out.println(agent.provideMove(HORIZONTAL_THREE, Tile.X_TILE));
        agent = new AlphaBetaAgent(4);
        System.out.println(agent.provideMove(HORIZONTAL_THREE, Tile.X_TILE));
    }

}
