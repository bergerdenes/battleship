package hu.bergerdenes.battleship.nodes;

import javafx.scene.shape.Rectangle;

import hu.bergerdenes.battleship.interfaces.Point;

public class Cell extends Rectangle {

    private final Point position;
    private boolean discovered = false;

    public Cell(Point position, double column, double row, double width, double height) {
        super(column, row, width, height);
        this.position = position;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void discover() {
        this.discovered = true;
    }

    public Point getPosition() {
        return position;
    }
}
