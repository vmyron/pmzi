package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import one.nmu.pmzi.Application;
import one.nmu.pmzi.ApplicationState;

import java.io.IOException;

public class AboutAdminController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private void showAbout(ActionEvent event) throws IOException {
        ApplicationState.operationJournal().about();
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.adminAboutScreen(stage);
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        ApplicationState.operationJournal().changePassword();
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.updatePasswordScreen(stage);
    }

    public void exit(ActionEvent actionEvent) {
        ApplicationState.registrationJournal().logOut();
        Platform.exit();
    }

    public void listUsers(ActionEvent actionEvent) throws IOException {
        ApplicationState.operationJournal().listUsers();
        Stage stage = ApplicationState.getStage();
        stage.close();
        Application.listUsers(stage);
    }
}
