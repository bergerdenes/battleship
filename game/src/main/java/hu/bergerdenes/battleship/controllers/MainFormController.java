package hu.bergerdenes.battleship.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.DARKGRAY;
import static javafx.scene.paint.Color.DODGERBLUE;
import static javafx.scene.paint.Color.RED;

import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.StrokeType;

import hu.bergerdenes.battleship.GameState;
import hu.bergerdenes.battleship.interfaces.Board;
import hu.bergerdenes.battleship.interfaces.Point;
import hu.bergerdenes.battleship.interfaces.ShipShotResult;
import hu.bergerdenes.battleship.interfaces.ShotResult;
import hu.bergerdenes.battleship.nodes.Cell;

public class MainFormController {

    private static final int BOARD_SIZE = 10;

    public MenuItem menuItemQuit;
    public MenuItem menuItemAboutAction;
    public AnchorPane anchorPane;
    public Label labelGameOver;
    public Label labelShots;
    public Label labelAccuracy;
    private AudioClip sinkClip;
    private AudioClip hitClip;
    private AudioClip shootClip;

    private Board board;
    private GameState gameState;
    private Cell[][] gridCells;
    private int shotsFired;
    private int hits;

    public void onMenuItemQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onMenuItemAboutAction(ActionEvent actionEvent) {
        AboutDialogController.showAboutDialog();
    }

    public void onMenuItemNew(ActionEvent actionEvent) {
        startNewGame();
    }

    private void startNewGame() {
        initGameBoard(BOARD_SIZE);
        addGridCells();
        gameState = GameState.PLAYING;
        shotsFired = 0;
        hits = 0;
        updateShotsFiredLabel();
        labelGameOver.setVisible(false);
    }

    @FXML
    private void initialize() {
        hitClip = loadClip("/audio/hit.wav");
        shootClip = loadClip("/audio/shoot.wav");
        sinkClip = loadClip("/audio/sink.wav");
        startNewGame();
    }

    private AudioClip loadClip(String resource) {
        URL url = getClass().getResource(resource);
        if (url == null) {
            throw new IllegalStateException("Cannot find " + resource + " on the classpath.");
        }
        return new AudioClip(url.toExternalForm());
    }

    private void initGameBoard(int boardSize) {
        this.board = new Board(boardSize);
        System.out.println(this.board);
    }

    private void addGridCells() {
        gridCells = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Point position = new Point(row, column);
                Cell cell = new Cell(position, 23.0 + column * 25.0, 24.0 + row * 25.0, 25.0, 25.0);
                cell.setFill(DARKGRAY);
                cell.setStroke(BLACK);
                cell.setStrokeType(StrokeType.INSIDE);
                cell.setStrokeWidth(0.3);
                cell.onMouseClickedProperty().setValue(this::onCellMouseClick);
                anchorPane.getChildren().add(cell);
                gridCells[row][column] = cell;
            }
        }
    }

    private void onCellMouseClick(MouseEvent event) {
        if (gameState == GameState.GAME_OVER) {
            return;
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getSource() instanceof Cell cell) {
                Point point = cell.getPosition();
                System.out.printf("Mouse clicked on rect (%d, %d)%n", point.row(), point.column());
                if (!cell.isDiscovered()) {
                    cell.discover();
                    shotsFired++;
                    updateShotsFiredLabel();
                    if (evaluateShot(board.shoot(point), cell)) {
//                        System.out.println("GAME OVER!");
                        gameState = GameState.GAME_OVER;
                        labelGameOver.setVisible(true);
                    }
                }
            }
        }
    }

    private void updateShotsFiredLabel() {
        labelShots.setText(String.format("%d shot%s", shotsFired, shotsFired == 1 ? "" : "s"));
    }

    private boolean evaluateShot(ShotResult shotResult, Cell cell) {
        System.out.println(board.toString());
        ShipShotResult shipShotResult = shotResult.shipShotResult();
        if (shipShotResult.hit()) {
            hits++;
            if (shipShotResult.sank()) {
                sinkClip.play();
            } else {
                hitClip.play();
            }
//            System.out.printf("You %s a ship!%n", shipShotResult.sank() ? "sank" : "hit");
            cell.setFill(RED);
        } else {
            shootClip.play();
//            System.out.println("You missed!");
            cell.setFill(DODGERBLUE);
        }
        labelAccuracy.setText(String.format("Accuracy: %d%%", (int) ((double) hits / (shotsFired == 0 ? 1 : shotsFired)  * 100)));
        return shotResult.gameOver();
    }

}
