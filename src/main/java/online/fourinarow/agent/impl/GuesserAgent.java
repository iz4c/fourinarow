package online.fourinarow.agent.impl;

import online.fourinarow.agent.api.IAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;

import java.util.Random;

/**
 * This agent is mostly for testing, and tries to place their
 * tile in a random position every time
 */
public class GuesserAgent implements IAgent {

    Random random = new Random();

    public int provideMove(Board board, Tile tile) {
        if (board.isBoardFull()) return -1;  // avoid a nasty infinite loop

        int attempt;
        do {
            attempt = random.nextInt(board.getWidth());
        } while (board.isColumnFull(attempt));

        return attempt;
    }

}
