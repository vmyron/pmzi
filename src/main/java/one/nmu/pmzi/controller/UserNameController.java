package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import one.nmu.pmzi.Application;
import one.nmu.pmzi.ApplicationState;
import one.nmu.pmzi.User;
import one.nmu.pmzi.UserState;

import java.io.IOException;
import java.util.Optional;

public class UserNameController {

    @FXML
    private Label alert;
    @FXML
    private TextField userName;
    public void findUser(ActionEvent actionEvent) {
        Optional<User> user = ApplicationState.getUserDB().get(userName.getText());
        user.ifPresentOrElse(u -> {
            if (u.getUserState() == UserState.BLOCKED) {
                alert.setText("Цього користувача заблоковано. Спробуйте іншого");
            } else {
                ApplicationState.setCurrentUser(u);
                Stage stage = ApplicationState.getStage();
                stage.close();
                try {
                    if (u.getUserState() == UserState.NEW) {
                        ApplicationState.operationJournal().setPassword();
                        Application.setPasswordScreen(stage);
                    } else {
                        Application.password(stage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.exit();
                }
            }
        }, () -> {
            alert.setText("Користувача не знайдено. Спробуйте ще раз");
        });
    }
}
