package hu.bergerdenes.battleship.engine;

import java.util.Arrays;

import hu.bergerdenes.battleship.interfaces.Point;
import hu.bergerdenes.battleship.interfaces.ShipShotResult;

public class Ship {
    private final Point topLeft;
    private final int size;
    private final Orientation orientation;
    private final State[] cells;
    private int hitCounter = 0;

    public Ship(Point topLeft, int size, Orientation orientation) {
        this.topLeft = topLeft;
        this.size = size;
        this.orientation = orientation;
        this.cells = initCells(size);
    }

    private State[] initCells(int size) {
        State[] cells = new State[size];
        Arrays.fill(cells, State.INTACT);
        return cells;
    }

    public boolean isSunk() {
        return this.hitCounter == this.size;
    }

    public ShipShotResult checkHit(Point hitPoint) {
        if (hitPoint.row() < this.topLeft.row() || hitPoint.row() > bottomRow() ||
            hitPoint.column() < this.topLeft.column() || hitPoint.column() > rightColumn()) {
            return new ShipShotResult(this.topLeft, this.size, this.orientation, this.cells, false, isSunk());
        }
        boolean wasHit = updateIfHit(this.orientation == Orientation.HORIZONTAL
            ? hitPoint.column() - this.topLeft.column()
            : hitPoint.row() - this.topLeft.row());
        return new ShipShotResult(this.topLeft, this.size, this.orientation, this.cells, wasHit, isSunk());
    }

    private boolean updateIfHit(int cellIndex) {
        if (this.cells[cellIndex] == State.INTACT) {
            this.cells[cellIndex] = State.HIT;
            this.hitCounter++;
            return true;
        }
        return false;
    }

    public boolean isAdjacentOrOverlapping(Ship otherShip) {
        return this.topLeft.row() - 1 <= otherShip.bottomRow() && otherShip.topLeft.row() <= this.bottomRow() + 1 &&
            this.topLeft.column() - 1 <= otherShip.rightColumn() && otherShip.topLeft.column() <= this.rightColumn() + 1;
    }

    private int bottomRow() {
        return topLeft.row() + (orientation == Orientation.VERTICAL ? size - 1 : 0);
    }

    private int rightColumn() {
        return topLeft.column() + (orientation == Orientation.HORIZONTAL ? size - 1 : 0);
    }

    @Override
    public String toString() {
        return "Ship{" +
            "topLeft=" + topLeft +
            ", size=" + size +
            ", orientation=" + orientation +
            ", cells=" + Arrays.toString(cells) +
            '}';
    }
}
