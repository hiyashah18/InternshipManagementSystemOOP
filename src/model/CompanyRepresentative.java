package model;

import exception.InvalidInputException;

public class CompanyRepresentative extends User {
    private static final long serialVersionUID = 1L;

    private final String companyId;

    public CompanyRepresentative(String id, String name, String email, String passwordHash,
                                 String companyId) throws InvalidInputException {
        super(id, name, email, passwordHash);
        this.companyId = companyId;
    }

    public String getCompanyId() { return companyId; }

    @Override
    public String getRole() { return "COMPANY"; }

    @Override
    public String getDashboardTitle() { return "Company Dashboard"; }

    @Override
    public String[][] getMenuLinks() {
        return new String[][] {
            {"Dashboard", "/dashboard"},
            {"My Internships", "/company/internships"},
            {"Post Internship", "/company/post"}
        };
    }

    @Override
    public String describe() {
        return super.describe() + " - Company ID " + companyId;
    }
}
