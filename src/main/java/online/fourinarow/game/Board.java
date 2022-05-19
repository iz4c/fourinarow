package online.fourinarow.game;

import java.util.Arrays;

public class Board {

    // 0,0 is bottom left, x,y is top right
    private final Tile[][] state;
    private final int width;
    private final int height;

    public Board(int width, int height) {
        this.state = new Tile[width][height];
        for (Tile[] column : this.state) {
            Arrays.fill(column, Tile.EMPTY);
        }

        this.width = width;
        this.height = height;
    }

    public Board(int width, int height, Tile[][] state) {
        this.state = state;
        this.width = width;
        this.height = height;
    }

    // parse a board string in the format "width|height|[tile data]"
    public Board(String boardString) {
        String[] data = boardString.split("\\|");
        this.width = Integer.parseInt(data[0]);
        this.height = Integer.parseInt(data[1]);

        String boardState = data[2];
        this.state = new Tile[width][height];
        for (int i = 0; i < boardState.length(); i++) {
            Tile tile;
            switch (boardState.charAt(i)) {
                case 'X':
                    tile = Tile.X_TILE;
                    break;
                case 'O':
                    tile = Tile.O_TILE;
                    break;
                default:
                    tile = Tile.EMPTY;
            }

            // these operations convert from the 1D input string to the 2D board state array
            this.state[i / height][i % height] = tile;
        }
    }

    /**
     *
     * @param columnIdx The column to drop a tile into
     * @param tile The type of tile to drop
     * @return The result of the move, such as if it is a win or the game continues
     */
    public MoveResult dropTile(int columnIdx, Tile tile) {
        if (isColumnFull(columnIdx)) return MoveResult.INVALID;

        // for each row, if next row is full or the bottom then place the tile here
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            // check if the row is the bottom row, or if the row below already has a tile
            if (rowIdx + 1 >= height || state[columnIdx][rowIdx + 1] != Tile.EMPTY) {
                state[columnIdx][rowIdx] = tile;

                // handle wins and draws
                if (WinChecker.checkWinInPosition(this, columnIdx, rowIdx)) {
                    if (tile == Tile.X_TILE) return MoveResult.X_WIN;
                    else if (tile == Tile.O_TILE) return MoveResult.O_WIN;
                }
                else if (isBoardFull()) return MoveResult.DRAW;
                else return MoveResult.CONTINUE;
            }
        }

        return MoveResult.INVALID;  // shouldn't be called but compiler likes this
    }

    // check if a column is full
    public boolean isColumnFull(int columnIdx) {
        // only check top of column due to gravity mechanic
        return state[columnIdx][0] != Tile.EMPTY;
    }

    // check if the board is full
    public boolean isBoardFull() {
        for (int columnIdx = 0; columnIdx < width; columnIdx++) {
            if (state[columnIdx][0] == Tile.EMPTY) return false;
        }

        return true;
    }

    // create an identical deep copy of this board
    public Board cloneBoard() {
        Tile[][] clonedArray = new Tile[this.width][this.height];
        for (int column = 0; column < this.width; column++) {
            // copy each column in one native call
            System.arraycopy(this.state[column], 0, clonedArray[column], 0, this.height);
        }

        return new Board(this.width, this.height, clonedArray);
    }

    // count the number of tiles that have been placed in the board
    public int getTurnCount() {
        int turnCount = 0;
        for (Tile[] column : this.state) {
            for (Tile tile : column) {
                if (tile != Tile.EMPTY) {
                    turnCount++;
                }
            }
        }

        return turnCount;
    }

    // easy way of storing to file
    public String toString() {
        // width and height might be double-digit so can't hardcode their length
        String header = width + "|" + height + "|";
        char[] boardString = new char[this.width * this.height];
        int i = 0;
        for (Tile[] column : state) {
            for (Tile tile : column) {
                boardString[i] = tile.toChar();
                i++;
            }
        }

        return header + new String(boardString);
    }

    // produce an ASCII-art style String of the game board
    public String getPrettyPrint() {
        StringBuilder out = new StringBuilder();

        for (int y = 0; y < height; y++) {
            out.append("| ");  // left edge of the board
            for (int x = 0; x < width; x++) {
                out.append(state[x][y].toChar()).append(" ");  // add the tile representation
            }
            out.append("|\n"); // right edge of the board
        }

        for (int i = 0; i < (width * 2) + 3; i++) out.append("-");  // bottom of the board

        return out.toString();
    }

    public Tile[][] getState() {
        return state;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
