package hu.bergerdenes.battleship.engine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ShipTest {

    public static Stream<Arguments> collisionProvider() {
        return Stream.of(
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 4, 3, Orientation.HORIZONTAL, true), // second ship overlaps first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 0, 5, Orientation.HORIZONTAL, false), // second ship is before first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 8, 2, Orientation.HORIZONTAL, false), // second ship is after first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 0, 6, 1, Orientation.HORIZONTAL, true), // second ship is inside first
            Arguments.of(0, 5, 3, Orientation.HORIZONTAL, 1, 5, 3, Orientation.HORIZONTAL, false), // second ship same horizontal location but different row

            Arguments.of(5, 0, 3, Orientation.VERTICAL, 4, 0, 3, Orientation.VERTICAL, true), // second ship overlaps first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 0, 0, 5, Orientation.VERTICAL, false), // second ship is before first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 8, 0, 2, Orientation.VERTICAL, false), // second ship is after first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 6, 0, 1, Orientation.VERTICAL, true), // second ship is inside first
            Arguments.of(5, 0, 3, Orientation.VERTICAL, 5, 1, 3, Orientation.VERTICAL, false), // second ship same horizontal location but different row

            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 4, 3, Orientation.VERTICAL, true), // second ship orthogonal and before first
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 3, 3, Orientation.VERTICAL, false), // second ship orthogonal and crosses first
            Arguments.of(4, 4, 3, Orientation.HORIZONTAL, 3, 7, 3, Orientation.VERTICAL, false), // second ship orthogonal and after first

            Arguments.of(4, 4, 3, Orientation.VERTICAL, 4, 3, 3, Orientation.HORIZONTAL, true), // second ship orthogonal and before first
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 3, 3, 3, Orientation.HORIZONTAL, false), // second ship orthogonal and crosses first
            Arguments.of(4, 4, 3, Orientation.VERTICAL, 7, 3, 3, Orientation.HORIZONTAL, false) // second ship orthogonal and after first

        );
    }

    @ParameterizedTest
    @MethodSource("collisionProvider")
    void hasCollision(int ship1Row, int ship1Col, int ship1Size, Orientation ship1Orientation,
                      int ship2Row, int ship2Col, int ship2Size, Orientation ship2Orientation,
                      boolean hasCollision) {
        Ship ship1 = new Ship(new Point(ship1Row, ship1Col), ship1Size, ship1Orientation);
        Ship ship2 = new Ship(new Point(ship2Row, ship2Col), ship2Size, ship2Orientation);
        assertThat(ship1.hasCollision(ship2)).isEqualTo(hasCollision);
    }
}