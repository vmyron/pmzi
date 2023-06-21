package one.nmu.pmzi;

public enum SystemEvent {
    LOGIN("Вхід в систему"),
    LOGOUT("Вихід із системи"),
    ABOUT("Перегляд інформації про програму"),
    ADD_USER("Додавання користувача"),
    EDIT_USER("Редагування користувача"),
    LIST_USERS("Перегляд списку користувачів"),
    PASSWORD_CHANGE("Зміна пароля"),
    PASSWORD_SET("Встановлення пароля"),
    INCORRECT_PASSWORD("Використано не правильний пароль");

    private final String description;

    private SystemEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
