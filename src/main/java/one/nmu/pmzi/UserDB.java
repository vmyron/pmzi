package one.nmu.pmzi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserDB {
    private static final String DB_FILE_NAME = "users.json";
    private static final String DEFAULT_ADMIN_NAME = "admin";
    private static final File DB_FILE = new File(System.getProperty("java.io.tmpdir")).toPath().resolve(DB_FILE_NAME).toFile();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Map<String, User> users = new HashMap<>();

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    public void init() throws IOException {
        System.out.println("Init DB...");
        if (DB_FILE.exists()) {
            System.out.println("DB file was found at " + DB_FILE.getAbsolutePath());
            read();
        } else {
            System.out.println("DB file was not found");
            create();
            System.out.println("DB file has created");
            ApplicationState.setFirstStart(true);
            ApplicationState.setCurrentUser(users.get(DEFAULT_ADMIN_NAME));
        }
        System.out.println("DB initialization finished successfully");
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public Optional<User> get(String name) {
        return Optional.ofNullable(users.get(name));
    }

    public void add(User user) throws IOException {
        users.put(user.getName(), user);
        save();
    }

    public void update(User user) throws IOException {
        users.remove(user.getName());
        add(user);
    }

    public boolean exists(String userName) {
        return users.containsKey(userName);
    }

    private void create() throws IOException {
        users.put(DEFAULT_ADMIN_NAME, createAdmin());
        if (DB_FILE.createNewFile()) {
            System.out.println("New DB was created");
        }
    }

    private User createAdmin() {
        User admin = new User();
        admin.setName(DEFAULT_ADMIN_NAME);
        admin.setRole(UserRole.ADMIN);
        admin.setPasswordPolicy(PasswordPolicy.RESTRICTED);
        admin.setUserState(UserState.NEW);
        users.put(admin.getName(), admin);
        return admin;
    }

    private void read() throws IOException {
        System.out.println("Read DB data");
        if (DB_FILE.length() != 0) {
            System.out.println("DB file is not empty");
            List<User> users = MAPPER.readValue(DB_FILE, new TypeReference<>() {
            });
            System.out.println(users.size() + " was found");
            users.forEach(user -> this.users.put(user.getName(), user));
            users.forEach(System.out::println);
            User admin = this.users.get(DEFAULT_ADMIN_NAME);
            if (admin == null) {
                admin = createAdmin();
            }
            if (admin.getUserState() == UserState.NEW) {
                ApplicationState.setFirstStart(true);
                ApplicationState.setCurrentUser(this.users.get(DEFAULT_ADMIN_NAME));
            }
        } else {
            System.out.println("DB file is empty");
            createAdmin();
            ApplicationState.setFirstStart(true);
            ApplicationState.setCurrentUser(users.get(DEFAULT_ADMIN_NAME));
        }
    }

    private void save() throws IOException {
        MAPPER.writeValue(DB_FILE, users.values());
    }



}
