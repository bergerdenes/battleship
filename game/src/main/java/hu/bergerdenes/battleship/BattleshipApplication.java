package hu.bergerdenes.battleship;

import static javafx.scene.paint.Color.BLACK;
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

    private MediaPlayer mediaPlayer;
    private Board board;

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlUrl = BattleshipApplication.class.getResource("/main-form.fxml");
        if (fxmlUrl == null) {
            throw new IllegalStateException("Cannot find /main-form.fxml on the classpath.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        findMediaPlayer(scene);
        AnchorPane anchorPane = getAnchorPane(scene);
        addGridCells(anchorPane, mediaPlayer);

        stage.setTitle("Battleship");
        stage.setScene(scene);
        stage.show();

        this.board = new Board(10);
        System.out.println(this.board);

    }

    private AnchorPane getAnchorPane(Scene scene) {
        return (AnchorPane) scene.getRoot().getChildrenUnmodifiable().stream().filter(n -> n instanceof AnchorPane).findFirst().get();
    }

    private void findMediaPlayer(Scene scene) {
        MediaView mediaView = (MediaView) scene.getRoot().getChildrenUnmodifiable().stream().filter(n -> n instanceof MediaView).findFirst().get();
        this.mediaPlayer = mediaView.getMediaPlayer();
    }

    private void addGridCells(AnchorPane anchorPane, MediaPlayer mediaPlayer) {
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Rectangle cell = new Rectangle(23.0 + column * 25.0, 24.0 + row * 25.0, 25.0, 25.0);
                cell.setFill(DODGERBLUE);
                cell.setStroke(BLACK);
                cell.setStrokeType(StrokeType.INSIDE);
                cell.setStrokeWidth(0.3);
                cell.onMouseClickedProperty().setValue(this::onMouseClick);
                cell.setUserData(new Point(row, column));
                anchorPane.getChildren().add(cell);
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

    private static boolean evaluateShot(ShotResult shotResult, Rectangle rect) {
        ShipShotResult shipShotResult = shotResult.shipShotResult();
        if (shipShotResult.hit()) {
            System.out.printf("You %s a ship!%n", shipShotResult.sank() ? "sank" : "hit");
            rect.setFill(RED);
        } else {
            System.out.println("You missed!");
        }
        return shotResult.gameOver();
    }

}