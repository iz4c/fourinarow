package online.fourinarow.agent.api;

import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;

public interface IAgent {

    /**
     * This method is how the game retrieves the tile an agent wants to play
     *
     * @param board The game board to make a move in
     * @param tile The tile you are playing as
     * @return Return an integer in the range 0 <= x < board.width
     */
    int provideMove(Board board, Tile tile);

}
