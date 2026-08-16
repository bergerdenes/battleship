module battleship.game {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires battleship.engine;

    exports hu.bergerdenes.battleship;

    opens hu.bergerdenes.battleship.controllers to javafx.fxml;
}