package service;

import exception.DataStorageException;
import exception.InternshipClosedException;
import exception.InternshipNotFoundException;
import exception.InternshipSystemException;
import exception.InvalidInputException;
import model.Company;
import model.Internship;
import util.DataStore;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InternshipService implements Manageable<Internship>, Searchable<Internship> {

    private final Map<String, Internship> internships = new LinkedHashMap<String, Internship>();
    private final DataStore<Internship> store;

    public InternshipService(DataStore<Internship> store) throws DataStorageException {
        this.store = store;
        for (Internship i : store.load()) {
            internships.put(i.getId(), i);
        }
    }

    public Internship post(Company company, String title, String location, String description,
                           int stipend, int durationMonths) throws InternshipSystemException {
        if (company == null) {
            throw new InvalidInputException("Company profile not found.");
        }
        Internship i = new Internship(IdGenerator.next("INT"), company.getId(), company.getName(),
                title, location, description, stipend, durationMonths);
        add(i);
        return i;
    }

    /** Search by keyword in title, company, location or description (open internships only). */
    @Override
    public List<Internship> search(String keyword) {
        return search(keyword, "");
    }

    /** METHOD OVERLOADING (compile-time polymorphism): same name, different parameters. */
    public List<Internship> search(String keyword, String location) {
        String k = normalize(keyword);
        String l = normalize(location);
        List<Internship> result = new ArrayList<Internship>();
        for (Internship i : internships.values()) {
            if (!i.isOpen()) {
                continue;
            }
            boolean keywordOk = k.isEmpty() || contains(i.getTitle(), k) || contains(i.getCompanyName(), k)
                    || contains(i.getLocation(), k) || contains(i.getDescription(), k);
            boolean locationOk = l.isEmpty() || contains(i.getLocation(), l);
            if (keywordOk && locationOk) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Internship> getByCompany(String companyId) {
        List<Internship> result = new ArrayList<Internship>();
        for (Internship i : internships.values()) {
            if (i.getCompanyId().equals(companyId)) {
                result.add(i);
            }
        }
        return result;
    }

    /** Returns the internship if it exists and is still open; otherwise throws. */
    public Internship findOpenById(String id) throws InternshipNotFoundException, InternshipClosedException {
        Internship i = internships.get(id);
        if (i == null) {
            throw new InternshipNotFoundException("This internship does not exist.");
        }
        if (!i.isOpen()) {
            throw new InternshipClosedException("This internship is closed for applications.");
        }
        return i;
    }

    public void close(String internshipId, String companyId) throws InternshipSystemException {
        Internship i = internships.get(internshipId);
        if (i == null) {
            throw new InternshipNotFoundException("This internship does not exist.");
        }
        if (!i.getCompanyId().equals(companyId)) {
            throw new InvalidInputException("You can only close your own internships.");
        }
        i.close();
        persist();
    }

    @Override
    public void add(Internship internship) throws DataStorageException {
        internships.put(internship.getId(), internship);
        persist();
    }

    @Override
    public boolean remove(String id) throws DataStorageException {
        boolean removed = internships.remove(id) != null;
        if (removed) {
            persist();
        }
        return removed;
    }

    @Override
    public Internship findById(String id) {
        return internships.get(id);
    }

    @Override
    public List<Internship> getAll() {
        return new ArrayList<Internship>(internships.values());
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private static boolean contains(String text, String lowerKeyword) {
        return text != null && text.toLowerCase().contains(lowerKeyword);
    }

    private void persist() throws DataStorageException {
        store.save(new ArrayList<Internship>(internships.values()));
    }
}
