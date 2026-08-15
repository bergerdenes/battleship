package hu.bergerdenes.battleship.engine;

import java.security.SecureRandom;

class Randomizer {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final int boardSize;

    Randomizer(int boardSize) {
        this.boardSize = boardSize;
    }

    Point getRandomPoint(int shipSize, Orientation orientation) {
        int row = secureRandom.nextInt(boardSize - (orientation == Orientation.HORIZONTAL ? shipSize : 0));
        int col = secureRandom.nextInt(boardSize - (orientation == Orientation.VERTICAL ? shipSize : 0));
        return new Point(row, col);
    }

    Orientation getRandomOrientation() {
        return secureRandom.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
    }
}
