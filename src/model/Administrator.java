package model;

import exception.InvalidInputException;

public class Administrator extends User {
    private static final long serialVersionUID = 1L;

    public Administrator(String id, String name, String email, String passwordHash)
            throws InvalidInputException {
        super(id, name, email, passwordHash);
    }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public String getDashboardTitle() { return "Administrator Dashboard"; }

    @Override
    public String[][] getMenuLinks() {
        return new String[][] {
            {"Dashboard", "/dashboard"},
            {"Manage Records", "/admin"}
        };
    }
}
