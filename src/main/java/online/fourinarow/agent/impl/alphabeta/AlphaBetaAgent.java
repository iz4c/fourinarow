package online.fourinarow.agent.impl.alphabeta;

import online.fourinarow.agent.api.IAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AlphaBetaAgent implements IAgent {

    private final int INITIAL_DEPTH;
    private static final Random rnd = new Random();

    public AlphaBetaAgent() {
        INITIAL_DEPTH = 13;
    }

    public AlphaBetaAgent(int depth) {
        INITIAL_DEPTH = depth;
    }

    public int provideMove(Board board, Tile tile) {
        long start = System.currentTimeMillis();

        // variable depth search implementation
        // TODO: change to time-based iterative deepening
        int depth = Math.max(INITIAL_DEPTH, INITIAL_DEPTH + (board.getTurnCount() - 14));
        AlphaBetaState state = new AlphaBetaState(board, tile, tile, depth, MoveResult.CONTINUE);

        List<AlphaBetaState> bestStates = new ArrayList<>();
        Integer bestValuation = null;
        AlphaBetaSearch search = new AlphaBetaSearch();
        for (AlphaBetaState child : state.getNextStates()) {
            int valuation = search.alphabeta(child, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, tile.getOpposite());
            if (tile == Tile.X_TILE) {
                if (bestValuation == null || valuation > bestValuation) {
                    bestStates.clear();
                    bestValuation = valuation;
                }
                if (valuation >= bestValuation) {
                    bestStates.add(child);
                }

            } else {
                if (bestValuation == null || valuation < bestValuation) {
                    bestStates.clear();
                    bestValuation = valuation;
                }
                if (valuation <= bestValuation) {
                    bestStates.add(child);
                }
            }
        }

        System.out.println("AlphaBeta search finished in " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("Looked at " + (search.getUniquePositions() + 1) + " unique positions with a maximum depth of " + depth);

        int column = bestStates.get(rnd.nextInt(bestStates.size())).getLastColumnPlayed();

        System.out.println("Playing " + column + " with assumed outcome " + bestValuation);
        System.out.println("(Tied best moves: " + bestStates.stream().map(abState -> Integer.toString(abState.getLastColumnPlayed())).collect(Collectors.joining(",")) + ")");

        return column;
    }

}
