package hu.bergerdenes.battleship.engine;

import java.security.SecureRandom;

class Randomizer {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final int boardSize;

    Randomizer(int boardSize) {
        this.boardSize = boardSize;
    }

    // TOOD: update test! check for bounds!
    Point getRandomPoint(int shipSize, Orientation orientation) {
        int row = secureRandom.nextInt(boardSize - (orientation == Orientation.HORIZONTAL ? shipSize : 1));
        int col = secureRandom.nextInt(boardSize - (orientation == Orientation.VERTICAL ? shipSize : 1));
        return new Point(row, col);
    }

    Orientation getRandomOrientation() {
        return secureRandom.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
    }
}
