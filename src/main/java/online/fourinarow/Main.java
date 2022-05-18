package online.fourinarow;

import online.fourinarow.agent.api.IAgent;
import online.fourinarow.agent.impl.alphabeta.AlphaBetaAgent;
import online.fourinarow.agent.impl.alphabeta.MultithreadABAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.MoveResult;
import online.fourinarow.game.Tile;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(7,6);
        System.out.println(board.getPrettyPrint());
        System.out.println(board.toString());

        IAgent playerOne = new AlphaBetaAgent();
        IAgent playerTwo = new MultithreadABAgent();

        int turn = -1;
        MoveResult moveResult;
        do {
            turn++;
            Tile tile = turn % 2 == 0 ? Tile.X_TILE : Tile.O_TILE;
            int placingIn = tile == Tile.X_TILE ? playerOne.provideMove(board, tile) : playerTwo.provideMove(board, tile);
            System.out.println("Placing " + tile.name() + " in " + placingIn);
            moveResult = board.dropTile(placingIn, tile);
            System.out.println(board.getPrettyPrint());

            if (moveResult == MoveResult.INVALID) {
                System.err.println("INVALID MOVE");
                break;
            }

        } while (!moveResult.hasWinner() && moveResult != MoveResult.DRAW);

        System.out.println("Result: " + moveResult.name());
        System.out.println(board.getPrettyPrint());
        System.out.println(board.toString());
        System.out.println("Result: " + moveResult.name());
    }
}
