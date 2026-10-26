package service;

import exception.DataStorageException;
import exception.DuplicateApplicationException;
import exception.InternshipSystemException;
import exception.InvalidInputException;
import model.Application;
import model.ApplicationStatus;
import model.Internship;
import util.DataStore;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApplicationService implements Applicable {

    private final Map<String, Application> applications = new LinkedHashMap<String, Application>();
    private final DataStore<Application> store;
    private final InternshipService internshipService;

    public ApplicationService(DataStore<Application> store, InternshipService internshipService)
            throws DataStorageException {
        this.store = store;
        this.internshipService = internshipService;
        for (Application a : store.load()) {
            applications.put(a.getId(), a);
        }
    }

    @Override
    public Application apply(String studentId, String internshipId) throws InternshipSystemException {
        Internship internship = internshipService.findOpenById(internshipId);   // may throw
        if (hasApplied(studentId, internship.getId())) {
            throw new DuplicateApplicationException("You have already applied for this internship.");
        }
        Application a = new Application(IdGenerator.next("APP"), studentId, internship.getId());
        applications.put(a.getId(), a);
        persist();
        return a;
    }

    public boolean hasApplied(String studentId, String internshipId) {
        for (Application a : applications.values()) {
            if (a.getStudentId().equals(studentId) && a.getInternshipId().equals(internshipId)) {
                return true;
            }
        }
        return false;
    }

    /** Only the company that owns the internship may accept or reject its applicants. */
    public void updateStatus(String applicationId, ApplicationStatus status, String companyId)
            throws InternshipSystemException {
        Application a = applications.get(applicationId);
        if (a == null) {
            throw new InvalidInputException("Application not found.");
        }
        Internship i = internshipService.findById(a.getInternshipId());
        if (i == null || !i.getCompanyId().equals(companyId)) {
            throw new InvalidInputException("You can only review applicants of your own internships.");
        }
        a.setStatus(status);
        persist();
    }

    public List<Application> getByStudent(String studentId) {
        List<Application> result = new ArrayList<Application>();
        for (Application a : applications.values()) {
            if (a.getStudentId().equals(studentId)) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Application> getByInternship(String internshipId) {
        List<Application> result = new ArrayList<Application>();
        for (Application a : applications.values()) {
            if (a.getInternshipId().equals(internshipId)) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Application> getAll() {
        return new ArrayList<Application>(applications.values());
    }

    public int countByStatus(List<Application> list, ApplicationStatus status) {
        int count = 0;
        for (Application a : list) {
            if (a.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    public void removeByStudent(String studentId) throws DataStorageException {
        removeWhere(studentId, true);
    }

    public void removeByInternship(String internshipId) throws DataStorageException {
        removeWhere(internshipId, false);
    }

    private void removeWhere(String id, boolean byStudent) throws DataStorageException {
        boolean changed = false;
        Iterator<Application> it = applications.values().iterator();
        while (it.hasNext()) {
            Application a = it.next();
            String key = byStudent ? a.getStudentId() : a.getInternshipId();
            if (key.equals(id)) {
                it.remove();
                changed = true;
            }
        }
        if (changed) {
            persist();
        }
    }

    private void persist() throws DataStorageException {
        store.save(new ArrayList<Application>(applications.values()));
    }
}
