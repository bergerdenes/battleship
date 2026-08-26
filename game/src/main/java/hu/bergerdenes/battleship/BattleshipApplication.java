package hu.bergerdenes.battleship;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BattleshipApplication extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = loadMainFormAndScene();
        showStage(stage);
    }

    private Scene loadMainFormAndScene() throws IOException {
        URL fxmlUrl = BattleshipApplication.class.getResource("/forms/main-form.fxml");
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

}