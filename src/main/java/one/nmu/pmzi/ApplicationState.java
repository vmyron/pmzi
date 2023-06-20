package one.nmu.pmzi;

import javafx.stage.Stage;

public class ApplicationState {
    private static boolean firstStart;
    private static User currentUser;

    private static final UserDB userDB = new UserDB();

    private static Stage stage = null;

    public static void setFirstStart(boolean firstStart) {
        ApplicationState.firstStart = firstStart;
    }

    public static void setCurrentUser(User currentUser) {
        ApplicationState.currentUser = currentUser;
        System.out.println("Current user set to " + currentUser.getName());
    }

    public static boolean isFirstStart() {
        return firstStart;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static UserDB getUserDB() {
        return userDB;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ApplicationState.stage = stage;
    }
}
