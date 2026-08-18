package hu.bergerdenes.battleship.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainFormController {

    public void onMenuitemQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onMenuItemAboutAction(ActionEvent actionEvent) {
        AboutDialogController.showAboutDialog();
    }

    @FXML
    private void initialize() {
        System.out.println("MainFormController initialized");
    }

}
