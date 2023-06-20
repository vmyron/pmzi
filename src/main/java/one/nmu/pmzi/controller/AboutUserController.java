package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import one.nmu.pmzi.Application;
import one.nmu.pmzi.ApplicationState;

import java.io.IOException;

public class AboutUserController {
    @FXML
    public MenuBar menuBar;

    public void showAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.userAboutScreen(stage);
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.updatePasswordScreen(stage);
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
