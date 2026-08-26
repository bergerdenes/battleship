package hu.bergerdenes.battleship.engine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hu.bergerdenes.battleship.interfaces.Point;

class ShipTest {

    public static Stream<Arguments> collisionProvider() {
        return Stream.of(
            // Horizontal with Horizontal
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 4, 3, Orientation.HORIZONTAL, true), // second ship overlaps first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 6, 1, Orientation.HORIZONTAL, true), // second ship is inside first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 0, 5, Orientation.HORIZONTAL, true), // second ship is adjacent before first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 0, 4, Orientation.HORIZONTAL, false), // second ship is before first with clearance
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 8, 2, Orientation.HORIZONTAL, true), // second ship is adjacent after first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 9, 2, Orientation.HORIZONTAL, false), // second ship is after first with clearance
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 1, 5, 3, Orientation.HORIZONTAL, true), // second ship in adjacent row
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 2, 5, 3, Orientation.HORIZONTAL, false), // second ship in separated row

            // Vertical with Vertical
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 4, 0, 3, Orientation.VERTICAL, true), // second ship overlaps first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 6, 0, 1, Orientation.VERTICAL, true), // second ship is inside first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 0, 0, 5, Orientation.VERTICAL, true), // second ship is adjacent before first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 0, 0, 4, Orientation.VERTICAL, false), // second ship is before first with clearance
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 8, 0, 2, Orientation.VERTICAL, true), // second ship is adjacent after first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 9, 0, 2, Orientation.VERTICAL, false), // second ship is after first with clearance
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 5, 1, 3, Orientation.VERTICAL, true), // second ship in adjacent column
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 5, 2, 3, Orientation.VERTICAL, false), // second ship in separated column

            // Horizontal with Vertical
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 4, 3, Orientation.VERTICAL, true), // second ship orthogonal and crosses first
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 3, 3, Orientation.VERTICAL, true), // second ship orthogonal and adjacent before first
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 2, 3, Orientation.VERTICAL, false), // second ship orthogonal and before first with clearance
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 7, 3, Orientation.VERTICAL, true), // second ship orthogonal and adjacent after first
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 8, 3, Orientation.VERTICAL, false), // second ship orthogonal and after first with clearance

            // Vertical with Horizontal
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 4, 3, 3, Orientation.HORIZONTAL, true), // second ship orthogonal and crosses first
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 3, 3, 3, Orientation.HORIZONTAL, true), // second ship orthogonal and adjacent before first
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 2, 3, 3, Orientation.HORIZONTAL, false), // second ship orthogonal and before first with clearance
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 7, 3, 3, Orientation.HORIZONTAL, true), // second ship orthogonal and adjacent after first
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 8, 3, 3, Orientation.HORIZONTAL, false), // second ship orthogonal and after first with clearance

            // Diagonal touching
            Arguments.of(2, 2, 2, Orientation.HORIZONTAL, 1, 0, 2, Orientation.HORIZONTAL, true), // second ship touches corner diagonally
            Arguments.of(2, 2, 2, Orientation.HORIZONTAL, 0, 0, 2, Orientation.HORIZONTAL, false) // second ship diagonal with clearance
        );
    }

    @ParameterizedTest
    @MethodSource("collisionProvider")
    void hasCollision(int ship1Row, int ship1Col, int ship1Size, Orientation ship1Orientation,
                      int ship2Row, int ship2Col, int ship2Size, Orientation ship2Orientation,
                      boolean hasCollision) {
        Ship ship1 = new Ship(new Point(ship1Row, ship1Col), ship1Size, ship1Orientation);
        Ship ship2 = new Ship(new Point(ship2Row, ship2Col), ship2Size, ship2Orientation);
        assertThat(ship1.isAdjacentOrOverlapping(ship2)).isEqualTo(hasCollision);
    }
}