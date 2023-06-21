package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import one.nmu.pmzi.*;

import java.io.IOException;

public class PasswordController {

    private static final int MAX_ATTEMPTS = 3;
    public TextField key;
    private int errorCount = 0;
    @FXML
    public PasswordField password;
    public Label alert;

    public void validatePassword(ActionEvent actionEvent) {
        User user = ApplicationState.getCurrentUser();
        String pass = password.getText();
        String encrypted = PasswordEncoder.encode(pass);
        String cipher = key.getText();
        String decrypted = VigenereCipher.decrypt(cipher, user.getCipher().getKey());
        if (user.getEncryptedPassword().equals(encrypted) && user.getCipher().getText().equalsIgnoreCase(decrypted)) {
            ApplicationState.registrationJournal().logIn();
            Stage stage = ApplicationState.getStage();
            stage.close();
            try {
                if (user.getRole() == UserRole.ADMIN) {
                    Application.adminMainScreen(stage);
                } else {
                    Application.userMainScreen(stage);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Platform.exit();
            }
        } else {
            ApplicationState.operationJournal().wrongPassword();
            if (errorCount == MAX_ATTEMPTS) {
                Platform.exit();
            }
            alert.setText("Введено не правильний пароль або ключ");
            errorCount++;
        }
    }
}
