package hu.bergerdenes.battleship;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.DARKGRAY;
import static javafx.scene.paint.Color.DODGERBLUE;
import static javafx.scene.paint.Color.RED;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import hu.bergerdenes.battleship.engine.Board;
import hu.bergerdenes.battleship.engine.Point;
import hu.bergerdenes.battleship.engine.ShipShotResult;
import hu.bergerdenes.battleship.engine.ShotResult;

public class BattleshipApplication extends Application {

    private static final int BOARD_SIZE = 10;

    private Scene scene;
    private MediaPlayer mediaPlayer;
    private Board board;
    private Rectangle[][] gridCells;
    private GameState gameState;

    @Override
    public void start(Stage stage) throws IOException {
        scene = loadMainFormAndScene();
        findMediaPlayer();
        showStage(stage);
        startNewGame();
    }

    private void startNewGame() {
        initGameBoard(BOARD_SIZE);
        addGridCells(getAnchorPane());
        gameState = GameState.PLAYING;
    }

    private Scene loadMainFormAndScene() throws IOException {
        URL fxmlUrl = BattleshipApplication.class.getResource("/main-form.fxml");
        if (fxmlUrl == null) {
            throw new IllegalStateException("Cannot find /main-form.fxml on the classpath.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        return new Scene(fxmlLoader.load(), 640, 480);
    }

    private void showStage(Stage stage) {
        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.show();
    }

    private void initGameBoard(int boardSize) {
        this.board = new Board(boardSize);
        System.out.println(this.board);
    }

    private AnchorPane getAnchorPane() {
        return (AnchorPane) scene.getRoot().getChildrenUnmodifiable().stream()
            .filter(n -> n instanceof AnchorPane)
            .findFirst()
            .get();
    }

    private void findMediaPlayer() {
        MediaView mediaView = (MediaView) scene.getRoot().getChildrenUnmodifiable().stream().filter(n -> n instanceof MediaView).findFirst().get();
        this.mediaPlayer = mediaView.getMediaPlayer();
    }

    private void addGridCells(AnchorPane anchorPane) {
        gridCells = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Rectangle cell = new Rectangle(23.0 + column * 25.0, 24.0 + row * 25.0, 25.0, 25.0);
                cell.setFill(DARKGRAY);
                cell.setStroke(BLACK);
                cell.setStrokeType(StrokeType.INSIDE);
                cell.setStrokeWidth(0.3);
                cell.onMouseClickedProperty().setValue(this::onMouseClick);
                cell.setUserData(new Point(row, column));
                anchorPane.getChildren().add(cell);
                gridCells[row][column] = cell;
            }
        }
    }

    private void onMouseClick(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getSource() instanceof Rectangle rect) {
                Point point = (Point) rect.getUserData();
                System.out.printf("Mouse clicked on rect (%d, %d)%n", point.row(), point.column());
                mediaPlayer.stop();
                mediaPlayer.play();
                evaluateShot(board.shoot(point), rect);
            }
        }
    }

    private boolean evaluateShot(ShotResult shotResult, Rectangle rect) {
        ShipShotResult shipShotResult = shotResult.shipShotResult();
        if (shipShotResult.hit()) {
            System.out.printf("You %s a ship!%n", shipShotResult.sank() ? "sank" : "hit");
            rect.setFill(RED);
        } else {
            System.out.println("You missed!");
            rect.setFill(DODGERBLUE);
        }
        return shotResult.gameOver();
    }

}