package hu.bergerdenes.battleship.engine;

import java.util.Arrays;

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
