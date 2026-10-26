package model;

import exception.InvalidInputException;
import util.Validator;

import java.io.Serializable;
import java.time.LocalDate;

public class Internship implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String companyId;
    private final String companyName;     // stored for quick display and searching
    private String title;
    private String location;
    private String description;
    private int stipend;                  // rupees per month
    private int durationMonths;
    private boolean open;
    private final LocalDate postedOn;

    public Internship(String id, String companyId, String companyName, String title, String location,
                      String description, int stipend, int durationMonths) throws InvalidInputException {
        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        setTitle(title);
        setLocation(location);
        setDescription(description);
        setStipend(stipend);
        setDurationMonths(durationMonths);
        this.open = true;
        this.postedOn = LocalDate.now();
    }

    public String getId() { return id; }
    public String getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public int getStipend() { return stipend; }
    public int getDurationMonths() { return durationMonths; }
    public boolean isOpen() { return open; }
    public LocalDate getPostedOn() { return postedOn; }

    public void setTitle(String title) throws InvalidInputException {
        this.title = Validator.requireText(title, "Title");
    }

    public void setLocation(String location) throws InvalidInputException {
        this.location = Validator.requireText(location, "Location");
    }

    public void setDescription(String description) throws InvalidInputException {
        this.description = Validator.requireText(description, "Description");
    }

    public void setStipend(int stipend) throws InvalidInputException {
        this.stipend = Validator.requireInRange(stipend, 0, 1000000, "Stipend");
    }

    public void setDurationMonths(int months) throws InvalidInputException {
        this.durationMonths = Validator.requireInRange(months, 1, 24, "Duration (months)");
    }

    public void close() { this.open = false; }

    @Override
    public String toString() {
        return title + " at " + companyName + " (" + location + ")";
    }
}
