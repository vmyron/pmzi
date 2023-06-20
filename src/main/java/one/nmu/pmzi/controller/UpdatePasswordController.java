package one.nmu.pmzi.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import one.nmu.pmzi.*;

import java.io.IOException;
import java.time.LocalDate;

public class UpdatePasswordController {
    private int errorCount = 0;
    private String errorText = "";
    private static final int PASSWORD_MAX_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;

    @FXML
    private Label alert;

    @FXML
    private PasswordField passwordOld;

    @FXML
    private PasswordField passwordFirst;

    @FXML
    private PasswordField passwordSecond;

    @FXML
    protected void validateAndSave() throws IOException {
        if (passwordValid()) {
            save();
        } else {
            onError();
        }
    }

    private void onError() {
        if (errorCount == MAX_ATTEMPTS) {
            Platform.exit();
        }
        alert.setText("Не правильний пароль! " + errorText);
        errorCount++;
        errorText = null;
    }

    private void save() throws IOException {
        User user = ApplicationState.getCurrentUser();
        user.setEncryptedPassword(PasswordEncoder.encode(passwordFirst.getText()));
        user.setPasswordLastUpdate(LocalDate.now());
        ApplicationState.getUserDB().update(user);
        Stage stage = ApplicationState.getStage();
        stage.close();
        if (user.getRole() == UserRole.ADMIN) {
            Application.adminMainScreen(stage);
        } else {
            Application.userMainScreen(stage);
        }
    }

    private boolean passwordValid() {
        String pass1 = passwordFirst.getText();
        String pass2 = passwordSecond.getText();
        String oldPass = passwordOld.getText();
        User user = ApplicationState.getCurrentUser();
        String encrypted = PasswordEncoder.encode(oldPass);
        if (!user.getEncryptedPassword().equals(encrypted)) {
            errorText = "Існуючий пароль вказано не вірно";
            return false;
        }
        if (pass1 == null || pass1.length() < PASSWORD_MAX_LENGTH) {
            errorText = "Довжина пароля менше мінімально допустимої";
            return false;
        }
        if (!ApplicationState.getCurrentUser().getPasswordPolicy().isPasswordCorrect(pass1)) {
            errorText = "Пароль має бути наступного формату: текст + числа + текст";
            return false;
        }
        if (!pass1.equals(pass2)) {
            errorText = "Паролі не співпадають";
            return false;
        }
        return true;
    }
}
