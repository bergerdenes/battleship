package hu.bergerdenes.battleship.engine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import hu.bergerdenes.battleship.interfaces.Point;

class RandomizerTest {

    private static final int BOARD_SIZE = 10;

    private Randomizer underTest;

    @BeforeEach
    void setUp() {
        underTest = new Randomizer(BOARD_SIZE);
    }

    @Nested
    class Horizontal {

        private static final int SHIP_SIZE = 5;

        private static int min = Integer.MAX_VALUE;
        private static int max = Integer.MIN_VALUE;

        @RepeatedTest(value = 100)
        void testGetRandomPointHorizontal() {
            Point point = underTest.getRandomPoint(SHIP_SIZE, Orientation.HORIZONTAL);
            System.out.printf("Generated point: (%d, %d)%n", point.row(), point.column());
            if (point.column() < min) {
                min = point.column();
            }
            if (point.column() > max) {
                max = point.column();
            }
            assertThat(point.row()).isBetween(0, BOARD_SIZE - 1);
            assertThat(point.column()).isBetween(0, BOARD_SIZE - SHIP_SIZE);
        }

        @AfterAll()
        static void afterAll() {
            assertThat(min).withFailMessage("Min value should be 0 but it was " + min).isEqualTo(0);
            assertThat(max).withFailMessage(String.format("Max value should be %d but it was %d", BOARD_SIZE - SHIP_SIZE, max)).isEqualTo(BOARD_SIZE - SHIP_SIZE);
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
        }

    }

    @Nested
    class Vertical {

        private static final int SHIP_SIZE = 4;
        private static int min = Integer.MAX_VALUE;
        private static int max = Integer.MIN_VALUE;

        @RepeatedTest(value = 100)
        void testGetRandomPointVertical() {
            Point point = underTest.getRandomPoint(SHIP_SIZE, Orientation.VERTICAL);
            System.out.printf("Generated point: (%d, %d)%n", point.row(), point.column());
            if (point.row() < min) {
                min = point.row();
            }
            if (point.row() > max) {
                max = point.row();
            }
            assertThat(point.row()).isBetween(0, BOARD_SIZE - SHIP_SIZE);
            assertThat(point.column()).isBetween(0, BOARD_SIZE - 1);
        }

        @AfterAll
        static void afterAll() {
            assertThat(min).withFailMessage("Min value should be 0 but it was " + min).isEqualTo(0);
            assertThat(max).withFailMessage(String.format("Max value should be %d but it was %d", BOARD_SIZE - SHIP_SIZE, max)).isEqualTo(BOARD_SIZE - SHIP_SIZE);
        }

    }
}