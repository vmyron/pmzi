package one.nmu.pmzi;

public enum PasswordPolicy {
    NONE,
    RESTRICTED("^[A-Za-z]+\\d+[A-Za-z]+$");

    private final String pattern;
    private PasswordPolicy(String pattern) {
        this.pattern = pattern;
    }
    private PasswordPolicy() {
        this.pattern = null;
    }
    public boolean isPasswordCorrect(String password) {
        if (pattern != null) {
            return password.matches(pattern);
        }
        return true;
    }
}
