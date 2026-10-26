package model;

import exception.InvalidInputException;
import util.Validator;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String course;
    private String skills;

    public Student(String id, String name, String email, String passwordHash,
                   String course, String skills) throws InvalidInputException {
        super(id, name, email, passwordHash);      // CONSTRUCTOR chaining
        setCourse(course);
        this.skills = skills == null ? "" : skills.trim();
    }

    public String getCourse() { return course; }
    public String getSkills() { return skills; }

    public void setCourse(String course) throws InvalidInputException {
        this.course = Validator.requireText(course, "Course");
    }

    public void setSkills(String skills) { this.skills = skills == null ? "" : skills.trim(); }

    @Override
    public String getRole() { return "STUDENT"; }

    @Override
    public String getDashboardTitle() { return "Student Dashboard"; }

    @Override
    public String[][] getMenuLinks() {
        return new String[][] {
            {"Dashboard", "/dashboard"},
            {"Browse Internships", "/internships"},
            {"My Applications", "/applications"}
        };
    }

    @Override
    public String describe() {
        return super.describe() + " - " + course;
    }
}
