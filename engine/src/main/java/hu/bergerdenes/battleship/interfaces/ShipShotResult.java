package hu.bergerdenes.battleship.interfaces;

import java.util.Arrays;

import hu.bergerdenes.battleship.engine.Orientation;
import hu.bergerdenes.battleship.engine.State;

public record ShipShotResult(
    Point topLeft,
    int size,
    Orientation orientation,
    State[] cells,
    boolean hit,
    boolean sank
) {

    @Override
    public State[] cells() {
        return Arrays.copyOf(cells, cells.length);
    }

}
