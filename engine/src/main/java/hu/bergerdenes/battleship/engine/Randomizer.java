package hu.bergerdenes.battleship.engine;

import java.security.SecureRandom;

import hu.bergerdenes.battleship.interfaces.Point;

public class Randomizer {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final int boardSize;

    public Randomizer(int boardSize) {
        this.boardSize = boardSize;
    }

    public Point getRandomPoint(int shipSize, Orientation orientation) {
        int row = secureRandom.nextInt(boardSize - (orientation == Orientation.VERTICAL ? shipSize - 1: 0));
        int col = secureRandom.nextInt(boardSize - (orientation == Orientation.HORIZONTAL ? shipSize - 1 : 0));
        return new Point(row, col);
    }

    public Orientation getRandomOrientation() {
        return secureRandom.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
    }
}
