package one.nmu.pmzi;

public interface Journal {

    default String getMessage(SystemEvent event) {
        User current = ApplicationState.getCurrentUser();
        return String.format("Дія: %s. Користувач: %s", event.getDescription(), current.getName());
    }
}
