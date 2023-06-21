package one.nmu.pmzi;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private String name;
    private UserRole role;
    private String encryptedPassword;
    private LocalDate passwordLastUpdate;
    private PasswordPolicy passwordPolicy;

    private UserState userState;

    private UserCipher cipher;

    public User() {
        this.role = UserRole.USER;
        this.userState = UserState.NEW;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public LocalDate getPasswordLastUpdate() {
        return passwordLastUpdate;
    }

    public void setPasswordLastUpdate(LocalDate passwordLastUpdate) {
        this.passwordLastUpdate = passwordLastUpdate;
    }

    public PasswordPolicy getPasswordPolicy() {
        return passwordPolicy;
    }

    public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public UserCipher getCipher() {
        return cipher;
    }

    public void setCipher(UserCipher cipher) {
        this.cipher = cipher;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", passwordLastUpdate=" + passwordLastUpdate +
                ", passwordPolicy=" + passwordPolicy +
                ", userState=" + userState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
