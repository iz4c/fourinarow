package online.fourinarow.agent.impl.alphabeta;

import online.fourinarow.agent.api.IAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MultithreadABAgent implements IAgent {

    private final int INITIAL_DEPTH;
    private static final Random rnd = new Random();

    public MultithreadABAgent() {
        INITIAL_DEPTH = 14;
    }

    public MultithreadABAgent(int depth) {
        INITIAL_DEPTH = depth;
    }

    public int provideMove(Board board, Tile tile) {
        long start = System.currentTimeMillis();
        // variable depth search implementation
        int depth = Math.max(INITIAL_DEPTH, INITIAL_DEPTH + (board.getTurnCount() - 14));
        AlphaBetaState state = new AlphaBetaState(board, tile, tile, depth, MoveResult.CONTINUE);

        AlphaBetaSearch search = new AlphaBetaSearch();
        List<AlphaBetaState> children = state.getNextStates();

        // evaluate the possible child states on different threads
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        CountDownLatch latch = new CountDownLatch(children.size());
        int[] valuations = new int[children.size()];
        for (int i = 0; i < children.size(); i++) {
            AlphaBetaState child = children.get(i);

            int finalI = i;
            threadPool.submit(() -> {
                int valuation = search.alphabeta(child, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, tile.getOpposite());
                valuations[finalI] = valuation;
                latch.countDown();
            });
        }

        try {
            latch.await();
            threadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // find the best move to make now that we have our evaluations
        List<AlphaBetaState> bestStates = new ArrayList<>();
        Integer bestValuation = null;
        for (int i = 0; i < children.size(); i++) {
            int valuation = valuations[i];
            AlphaBetaState child = children.get(i);

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

        System.out.println("Multithreaded AlphaBeta search finished in " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("Looked at " + (search.getUniquePositions() + 1) + " unique positions with a maximum depth of " + depth);

        int column = bestStates.get(rnd.nextInt(bestStates.size())).getLastColumnPlayed();

        System.out.println("Playing " + column + " with assumed outcome " + bestValuation);
        System.out.println("(Tied best moves: " + bestStates.stream().map(abState -> Integer.toString(abState.getLastColumnPlayed())).collect(Collectors.joining(",")) + ")");

        return column;
    }

}
