package online.fourinarow.game;

public enum MoveResult {
    INVALID,
    CONTINUE,
    X_WIN,
    O_WIN,
    DRAW;

    public boolean hasWinner() {
        return this == X_WIN || this == O_WIN;
    }
}
