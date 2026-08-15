package hu.bergerdenes.battleship;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import hu.bergerdenes.battleship.engine.Board;
import hu.bergerdenes.battleship.engine.Point;
import hu.bergerdenes.battleship.engine.ShipShotResult;
import hu.bergerdenes.battleship.engine.ShotResult;

public class Starter {
    private static final Scanner scanner = new Scanner(System.in);

    static void main(String[] args) {
        Board board = new Board(10);
//        System.out.println(board);
        System.out.println("Game starts.");
        LocalDateTime startTime = LocalDateTime.now();
        boolean gameOver;
        do {
            int row = readInt("row");
            int column = readInt("column");
            ShotResult shotResult = board.shoot(new Point(row - 1, column - 1));
            gameOver = evaluateShot(shotResult);
        } while (!gameOver);
        LocalDateTime endTime = LocalDateTime.now();
        System.out.printf("You sank all of the ships!%nGame is over! Your time is %s seconds!%n", Duration.between(startTime, endTime).toSeconds());
    }

    private static boolean evaluateShot(ShotResult shotResult) {
        ShipShotResult shipShotResult = shotResult.shipShotResult();
        if (shipShotResult.hit()) {
            System.out.printf("You %s a ship!%n", shipShotResult.sank() ? "sank" : "hit");
        } else {
            System.out.println("You missed!");
        }
        return shotResult.gameOver();
    }

    static int readInt(String coordinate) {
        int n;
        do {
            System.out.printf("Enter %s coordinate: ", coordinate);
            try {
                n = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                n = 0;
            }
            if (n < 1 || n > 10) {
                System.out.println("Please enter number between 1 and 10.");
            }
        } while (n < 1 || n > 10);
        return n;
    }
}
