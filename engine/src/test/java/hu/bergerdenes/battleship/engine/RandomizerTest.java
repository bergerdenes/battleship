package hu.bergerdenes.battleship.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class RandomizerTest {

    private Randomizer underTest;

    @BeforeEach
    void setUp() {
        underTest = new Randomizer(10);
    }

    @RepeatedTest(value = 100)
    void testGetRandomPointVertical() {
        Point point = underTest.getRandomPoint(5, Orientation.HORIZONTAL);
        System.out.printf("Generated point: (%d, %d)%n", point.row(), point.column());
        assertTrue(point.row() >= 0 && point.row() <= 5);
        assertTrue(point.column() >= 0 && point.column() <= 9);
    }

    @RepeatedTest(value = 100)
    void testGetRandomPointHorizontal() {
        Point point = underTest.getRandomPoint(5, Orientation.VERTICAL);
        System.out.printf("Generated point: (%d, %d)%n", point.row(), point.column());
        assertTrue(point.row() >= 0 && point.row() <= 9);
        assertTrue(point.column() >= 0 && point.column() <= 5);
    }

}