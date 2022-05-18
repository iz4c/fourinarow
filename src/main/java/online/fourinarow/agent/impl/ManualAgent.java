package online.fourinarow.agent.impl;

import online.fourinarow.agent.api.IAgent;
import online.fourinarow.game.Board;
import online.fourinarow.game.Tile;

import java.util.Scanner;

public class ManualAgent implements IAgent {

    /**
     * This agent enables a human to play against one of the automated agents by
     * entering the column they wish to play in on the command line
     *
     * @param board The game board to make a move in
     * @param tile The tile you are playing as
     * @return Return an integer entered through the command line
     */
    public int provideMove(Board board, Tile tile) {
        if (board.isBoardFull()) return -1;  // avoid a nasty infinite loop

        Scanner inp = new Scanner(System.in);

        return inp.nextInt();
    }

}
