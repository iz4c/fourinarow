package online.fourinarow.agent.impl.alphabeta;

import online.fourinarow.game.Tile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlphaBetaSearch {

    private final Map<String, Integer> cache = new ConcurrentHashMap<>();
    private long uniquePositions = 0;

    public AlphaBetaSearch() {

    }

    // https://en.wikipedia.org/wiki/Minimax#Pseudocode
    public int alphabeta(AlphaBetaState state, int depth, int alpha, int beta, Tile maximisingPlayer) {
        String boardString = state.getBoard().toString();
        if (cache.containsKey(boardString)) return cache.get(boardString);

        uniquePositions++;

        List<AlphaBetaState> childStates = state.getNextStates();
        if (depth == 0 || childStates.isEmpty()) {
            int positionValue;
            switch (state.getGameState()) {
                case X_WIN:
                    positionValue = Integer.MAX_VALUE - state.getBoard().getTurnCount();
                    break;
                case O_WIN:
                    positionValue = Integer.MIN_VALUE + state.getBoard().getTurnCount();
                    break;
                case DRAW:
                    positionValue = 0;
                    break;
                default:  // this is when we reach the end of our search but there's no guaranteed outcome
                    positionValue = 0;
                    break;
            }

            cache.put(boardString, positionValue);
            return positionValue;
        }

        if (maximisingPlayer == Tile.X_TILE) {
            int value = Integer.MIN_VALUE;
            for (AlphaBetaState child : childStates) {
                value = Math.max(value, alphabeta(child, depth - 1, alpha, beta, Tile.O_TILE));
                if (value >= beta) {
                    break;
                }
                alpha = Math.max(alpha, value);
            }
            cache.put(boardString, value);
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (AlphaBetaState child : childStates) {
                value = Math.min(value, alphabeta(child, depth - 1, alpha, beta, Tile.X_TILE));
                if (value <= alpha) {
                    break;
                }
                beta = Math.min(beta, value);
            }
            cache.put(boardString, value);
            return value;
        }
    }

    public long getUniquePositions() {
        return uniquePositions;
    }

}
