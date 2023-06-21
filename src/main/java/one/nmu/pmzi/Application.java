package one.nmu.pmzi;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationState.setStage(stage);
        if (ApplicationState.isFirstStart()) {
            ApplicationState.operationJournal().setPassword();
            setPasswordScreen(stage);
        } else {
            userName(stage);
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationState.getUserDB().init();
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://do.nmu.org.ua/user/view.php?id=51427&course=5227");
        launch();
    }

    public static void setPasswordScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("set-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Встановлення паролю");
        stage.setScene(scene);
        stage.show();
    }

    public static void updatePasswordScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("update-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Зміна паролю");
        stage.setScene(scene);
        stage.show();
    }

    public static void adminMainScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Основна сторінка");
        stage.setScene(scene);
        stage.show();
    }

    public static void userMainScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Основна сторінка");
        stage.setScene(scene);
        stage.show();
    }

    public static void adminAboutScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("about-admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Про програму");
        stage.setScene(scene);
        stage.show();
    }

    public static void userAboutScreen(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("about-user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Про програму");
        stage.setScene(scene);
        stage.show();
    }

    public static void userName(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("user-name-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Ідентифікація");
        stage.setScene(scene);
        stage.show();
    }

    public static void password(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Аутентифікація");
        stage.setScene(scene);
        stage.show();
    }

    public static void listUsers(Stage stage) throws IOException {
        TableView<User> userTable = initTable();
        Collection<User> users = ApplicationState.getUserDB().getAll();
        users.forEach(u->userTable.getItems().add(u));
        MenuBar menuBar = initMenu(stage);
        Label alert = new Label();
        alert.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        Button edit = new Button("Редагувати");
        edit.setOnAction(e -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            if (user != null) {
                ApplicationState.operationJournal().editUser();
                stage.close();
                editUser(stage, user);
            } else {
                alert.setText("Спочатку виберіть користувача для редагування");
            }
        });
        userTable.getSelectionModel().selectFirst();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, userTable, alert, edit);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        stage.setTitle("Список користувачів");
        stage.setScene(scene);
        stage.show();
    }

    public static void addUser(Stage stage) {
        MenuBar menuBar = initMenu(stage);
        Label alert = new Label();
        alert.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        Label lab1 = new Label("Ім'я користувача");
        TextField name = new TextField();
        lab1.setLabelFor(name);
        Label lab2 = new Label("Політика пароля");
        ChoiceBox<PasswordPolicy> policies = new ChoiceBox<>();
        lab2.setLabelFor(policies);
        policies.getItems().add(PasswordPolicy.NONE);
        policies.getItems().add(PasswordPolicy.RESTRICTED);
        Button saveButton = new Button();
        saveButton.setText("Зберегти");
        saveButton.setOnAction(e -> {
            String tmpName = name.getText();
            PasswordPolicy tmpPolicy = policies.getValue();
            if (tmpName != null && tmpPolicy != null) {
                if (ApplicationState.getUserDB().exists(tmpName)) {
                    alert.setText("Користувач з таки ім'ям вже існує");
                } else {
                    User user = new User();
                    user.setName(tmpName);
                    user.setPasswordPolicy(tmpPolicy);
                    try {
                        ApplicationState.getUserDB().add(user);
                        stage.close();
                        adminMainScreen(stage);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Platform.exit();
                    }
                }
            } else {
                alert.setText("Не всі поля заповнені. Неможливо зберегти зміни");
            }
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, alert, lab1, name, lab2, policies, saveButton);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        stage.setTitle("Додати користувача");
        stage.setScene(scene);
        stage.show();
    }

    public static void editUser(Stage stage, User user) {
        MenuBar menuBar = initMenu(stage);
        Label alert = new Label();
        alert.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        Label lab1 = new Label("Ім'я користувача");
        TextField name = new TextField();
        lab1.setLabelFor(name);
        name.setText(user.getName());
        name.setEditable(false);
        Label lab2 = new Label("Політика пароля");
        ChoiceBox<PasswordPolicy> policies = new ChoiceBox<>();
        lab2.setLabelFor(policies);
        policies.getItems().add(PasswordPolicy.NONE);
        policies.getItems().add(PasswordPolicy.RESTRICTED);
        policies.setValue(user.getPasswordPolicy());
        Label lab3 = new Label("Стан");
        ChoiceBox<UserState> states = new ChoiceBox<>();
        lab3.setLabelFor(states);
        states.getItems().add(UserState.ACTIVE);
        states.getItems().add(UserState.BLOCKED);
        states.setValue(user.getUserState());
        Button saveButton = new Button();
        saveButton.setText("Зберегти");
        saveButton.setOnAction(e -> {
            PasswordPolicy tmpPolicy = policies.getValue();
            UserState tmpState = states.getValue();
            if (tmpPolicy != null && tmpState != null) {
                user.setUserState(tmpState);
                user.setPasswordPolicy(tmpPolicy);
                try {
                    ApplicationState.getUserDB().update(user);
                    stage.close();
                    listUsers(stage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Platform.exit();
                }
            } else {
                alert.setText("Не всі поля заповнені. Неможливо зберегти зміни");
            }
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, alert, lab1, name, lab2, policies, lab3, states, saveButton);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        stage.setTitle("Редагування користувача");
        stage.setScene(scene);
        stage.show();
    }
    private static TableView<User> initTable() {
        TableView<User> userTable = new TableView<>();
        TableColumn<User, String> column1 = new TableColumn<>("Ім'я");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, UserRole> column2 = new TableColumn<>("Роль");
        column2.setCellValueFactory(new PropertyValueFactory<>("role"));
        TableColumn<User, LocalDate> column3 = new TableColumn<>("Пароль оновлено");
        column3.setCellValueFactory(new PropertyValueFactory<>("passwordLastUpdate"));
        TableColumn<User, PasswordPolicy> column4 = new TableColumn<>("Політика паролів");
        column4.setCellValueFactory(new PropertyValueFactory<>("passwordPolicy"));
        TableColumn<User, UserState> column5 = new TableColumn<>("Стан");
        column5.setCellValueFactory(new PropertyValueFactory<>("userState"));
        userTable.getColumns().add(column1);
        userTable.getColumns().add(column2);
        userTable.getColumns().add(column3);
        userTable.getColumns().add(column4);
        userTable.getColumns().add(column5);
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return userTable;
    }

    private static MenuBar initMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu actions = new Menu("Дії");
        MenuItem users = new MenuItem("Користувачі");
        users.setOnAction(e -> {
            ApplicationState.operationJournal().listUsers();
            stage.close();
            try {
                listUsers(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
        MenuItem password = new MenuItem("Зміна пароля");
        password.setOnAction(e -> {
            ApplicationState.operationJournal().changePassword();
            stage.close();
            try {
                updatePasswordScreen(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
        MenuItem exit = new MenuItem("Вихід");
        exit.setOnAction(e -> {
            ApplicationState.registrationJournal().logOut();
            Platform.exit();
        });
        actions.getItems().add(users);
        actions.getItems().add(password);
        actions.getItems().add(new SeparatorMenuItem());
        actions.getItems().add(exit);
        Menu help = new Menu("Довідка");
        MenuItem about = new MenuItem("Про програму");
        about.setOnAction(e -> {
            ApplicationState.operationJournal().about();
            stage.close();
            try {
                adminAboutScreen(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
        help.getItems().add(about);
        menuBar.getMenus().add(actions);
        menuBar.getMenus().add(help);
        return menuBar;
    }
}