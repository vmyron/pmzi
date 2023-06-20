package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import one.nmu.pmzi.Application;
import one.nmu.pmzi.ApplicationState;

import java.io.IOException;

public class MainAdminController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private void showAbout(ActionEvent event) throws IOException {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.adminAboutScreen(stage);
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.updatePasswordScreen(stage);
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void listUsers(ActionEvent actionEvent) throws IOException {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.listUsers(stage);
    }

    public void addUser(ActionEvent actionEvent) {
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.addUser(stage);
    }
}
