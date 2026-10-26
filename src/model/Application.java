package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String studentId;
    private final String internshipId;
    private ApplicationStatus status;
    private final LocalDate appliedOn;

    public Application(String id, String studentId, String internshipId) {
        this.id = id;
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = ApplicationStatus.PENDING;
        this.appliedOn = LocalDate.now();
    }

    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getInternshipId() { return internshipId; }
    public ApplicationStatus getStatus() { return status; }
    public LocalDate getAppliedOn() { return appliedOn; }

    public void setStatus(ApplicationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Application " + id + " [" + status + "]";
    }
}
