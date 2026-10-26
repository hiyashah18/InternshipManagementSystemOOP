package model;

import exception.InvalidInputException;
import util.Validator;

import java.io.Serializable;

/**
 * ABSTRACTION + INHERITANCE: common data and behaviour of every user.
 * Student, CompanyRepresentative and Administrator extend this class.
 * ENCAPSULATION: all fields are private and reached through getters/setters.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;
    private String email;
    private String passwordHash;

    protected User(String id, String name, String email, String passwordHash) throws InvalidInputException {
        this.id = id;
        setName(name);
        setEmail(email);
        this.passwordHash = passwordHash;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) throws InvalidInputException {
        this.name = Validator.requireText(name, "Name");
    }

    public void setEmail(String email) throws InvalidInputException {
        this.email = Validator.requireEmail(email);
    }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public boolean passwordMatches(String hash) {
        return passwordHash != null && passwordHash.equals(hash);
    }

    // ---- abstract methods: every subclass MUST give its own version (POLYMORPHISM) ----
    public abstract String getRole();

    public abstract String getDashboardTitle();

    /** Menu entries {label, url} shown in the navigation bar for this type of user. */
    public abstract String[][] getMenuLinks();

    /** Overridden by subclasses (runtime polymorphism). */
    public String describe() {
        return getRole() + ": " + name + " (" + email + ")";
    }

    @Override
    public String toString() {
        return describe();
    }
}
