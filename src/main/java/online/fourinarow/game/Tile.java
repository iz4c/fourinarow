package online.fourinarow.game;

public enum Tile {

    EMPTY(0),
    X_TILE(1),
    O_TILE(-1);

    private final int value;

    Tile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Tile getOpposite() {
        switch (this) {
            case X_TILE: return O_TILE;
            case O_TILE: return X_TILE;
            default: return EMPTY;
        }
    }

    public boolean isWinner(MoveResult result) {
        if (result == MoveResult.X_WIN && this == X_TILE) return true;
        else if (result == MoveResult.O_WIN && this == O_TILE) return true;
        else return false;
    }

    // this is a more efficient toString, since chars are primitives not objects
    public char toChar() {
        switch (value) {
            case 0:
                return '-';
            case 1:
                return 'X';
            case -1:
                return 'O';
            default:  // this shouldn't ever trigger, but the compiler needs it
                return '?';
        }
    }

    public String toString() {
        switch (value) {
            case 0:
                return "-";
            case 1:
                return "X";
            case -1:
                return "O";
            default:  // this shouldn't ever trigger but the compiler needs it
                return "?";
        }
    }

}
