package model;

import exception.InvalidInputException;
import util.Validator;

import java.io.Serializable;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String industry;
    private String location;

    /** Default constructor. */
    public Company() { }

    /** Parameterized constructor. */
    public Company(String id, String name, String industry, String location) throws InvalidInputException {
        this.id = id;
        setName(name);
        setIndustry(industry);
        setLocation(location);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getIndustry() { return industry; }
    public String getLocation() { return location; }

    public void setName(String name) throws InvalidInputException {
        this.name = Validator.requireText(name, "Company name");
    }

    public void setIndustry(String industry) throws InvalidInputException {
        this.industry = Validator.requireText(industry, "Industry");
    }

    public void setLocation(String location) throws InvalidInputException {
        this.location = Validator.requireText(location, "Company location");
    }

    @Override
    public String toString() {
        return name + " (" + industry + ", " + location + ")";
    }
}
