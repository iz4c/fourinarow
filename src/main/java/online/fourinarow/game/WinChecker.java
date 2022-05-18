package online.fourinarow.game;

public class WinChecker {

    static final int LENGTH = 4;
    public static long nanosOnChecks = 0;

    // detect if either play has won on a game board
    // this is very inefficient, since it scans the whole board for a win every time
    // in reality, you only need to search where the most recent tile was played since nothing else has changed
    public static boolean isWinning(Board board) {
        long start = System.nanoTime();
        Tile[][] state = board.getState();

        // check verticals
        for (int column = 0; column < board.getWidth(); column++) {
            Tile lastTile = Tile.EMPTY;
            int consecutive = 0;

            for (int row = 0; row < board.getHeight(); row++) {
                if (state[column][row] != lastTile || state[column][row] == Tile.EMPTY) {
                    consecutive = 0;
                } else {
                    if (consecutive >= LENGTH - 1) {  // -1 since 3 matches after the first means 4 in a row
                        nanosOnChecks += System.nanoTime() - start;
                        return true;
                    }
                }

                lastTile = state[column][row];
                consecutive++;
            }
        }

        // check horizontals
        for (int row = 0; row < board.getHeight(); row++) {
            Tile lastTile = Tile.EMPTY;
            int consecutive = 0;

            for (int column = 0; column < board.getWidth(); column++) {
                if (state[column][row] != lastTile || state[column][row] == Tile.EMPTY) {
                    consecutive = 0;
                } else {
                    if (consecutive >= LENGTH - 1) {  // -1 since 3 matches after the first means 4 in a row
                        nanosOnChecks += System.nanoTime() - start;
                        return true;
                    }
                }

                lastTile = state[column][row];
                consecutive++;
            }
        }

        // check diagonals
        // first check top-left to bottom-right diagonals
        for (int column = 0; column < board.getWidth() - (LENGTH - 1); column++) {
            for (int row = 0; row < board.getHeight() - (LENGTH - 1); row++) {
                Tile tile = state[column][row];
                if (tile == Tile.EMPTY) continue;

                boolean match = true;
                for (int i = 0; i < LENGTH; i++) {
                    if (state[column + i][row + i] != tile) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }
        }

        // then check bottom-left to top-right diagonals
        for (int column = 0; column < board.getWidth() - (LENGTH - 1); column++) {
            for (int row = LENGTH - 1; row < board.getHeight(); row++) {
                Tile tile = state[column][row];
                if (tile == Tile.EMPTY) continue;

                boolean match = true;
                for (int i = 0; i < LENGTH; i++) {
                    if (state[column + i][row - i] != tile) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }
        }

        nanosOnChecks += System.nanoTime() - start;
        return false;
    }

    // a slimmed down isWinning that checks the most recent move instead of the whole board
    public static boolean checkWinInPosition(Board board, int tileColumn, int tileRow) {
        long start = System.nanoTime();
        Tile[][] state = board.getState();

        // check verticals
        Tile lastTile = Tile.EMPTY;
        int consecutive = 0;

        for (int row = 0; row < board.getHeight(); row++) {
            if (state[tileColumn][row] != lastTile || state[tileColumn][row] == Tile.EMPTY) {
                consecutive = 0;
            } else {
                if (consecutive >= LENGTH - 1) {  // -1 since 3 matches after the first means 4 in a row
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }

            lastTile = state[tileColumn][row];
            consecutive++;
        }

        // check horizontals
        lastTile = Tile.EMPTY;
        consecutive = 0;

        for (int column = 0; column < board.getWidth(); column++) {
            if (state[column][tileRow] != lastTile || state[column][tileRow] == Tile.EMPTY) {
                consecutive = 0;
            } else {
                if (consecutive >= LENGTH - 1) {  // -1 since 3 matches after the first means 4 in a row
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }

            lastTile = state[column][tileRow];
            consecutive++;
        }

        // check diagonals
        // first check top-left to bottom-right diagonals
        for (int column = 0; column < board.getWidth() - (LENGTH - 1); column++) {
            for (int row = 0; row < board.getHeight() - (LENGTH - 1); row++) {
                Tile tile = state[column][row];
                if (tile == Tile.EMPTY) continue;

                boolean match = true;
                for (int i = 0; i < LENGTH; i++) {
                    if (state[column + i][row + i] != tile) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }
        }

        // then check bottom-left to top-right diagonals
        for (int column = 0; column < board.getWidth() - (LENGTH - 1); column++) {
            for (int row = LENGTH - 1; row < board.getHeight(); row++) {
                Tile tile = state[column][row];
                if (tile == Tile.EMPTY) continue;

                boolean match = true;
                for (int i = 0; i < LENGTH; i++) {
                    if (state[column + i][row - i] != tile) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    nanosOnChecks += System.nanoTime() - start;
                    return true;
                }
            }
        }

        nanosOnChecks += System.nanoTime() - start;
        return false;
    }

}
