package hu.bergerdenes.battleship.controllers;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class AboutDialogController  {

    public static void showAboutDialog() {
        Dialog<AboutDialogController> dialog = new Dialog<>();
        dialog.setContentText("© Berger Dénes, 2026");
        dialog.setHeaderText("Single-player Battleship game");
        dialog.setTitle("Battleship");
        dialog.setResizable(false);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setOnMouseClicked(event -> {
            dialog.close();
        });
        dialog.showAndWait();
    }
}
