package service;

import exception.AuthenticationException;
import exception.DataStorageException;
import exception.InternshipSystemException;
import exception.InvalidInputException;
import model.Administrator;
import model.CompanyRepresentative;
import model.Student;
import model.User;
import util.DataStore;
import util.IdGenerator;
import util.PasswordUtil;
import util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Manages all users (students, company representatives, administrators). */
public class UserService implements Manageable<User> {

    // COLLECTION: a map from user id to user (LinkedHashMap is a HashMap that keeps insertion order)
    private final Map<String, User> users = new LinkedHashMap<String, User>();
    private final DataStore<User> store;

    public UserService(DataStore<User> store) throws DataStorageException {
        this.store = store;
        for (User u : store.load()) {
            users.put(u.getId(), u);
        }
    }

    public Student registerStudent(String name, String email, String password,
                                   String course, String skills) throws InternshipSystemException {
        ensureEmailFree(email);
        Student s = new Student(IdGenerator.next("STU"), name, email,
                PasswordUtil.hash(Validator.requirePassword(password)), course, skills);
        add(s);
        return s;
    }

    public CompanyRepresentative registerCompanyRep(String name, String email, String password,
                                                    String companyId) throws InternshipSystemException {
        ensureEmailFree(email);
        CompanyRepresentative rep = new CompanyRepresentative(IdGenerator.next("REP"), name, email,
                PasswordUtil.hash(Validator.requirePassword(password)), companyId);
        add(rep);
        return rep;
    }

    public Administrator registerAdmin(String name, String email, String password)
            throws InternshipSystemException {
        ensureEmailFree(email);
        Administrator a = new Administrator(IdGenerator.next("ADM"), name, email,
                PasswordUtil.hash(Validator.requirePassword(password)));
        add(a);
        return a;
    }

    public void ensureEmailFree(String email) throws InvalidInputException {
        String normalized = Validator.requireEmail(email);
        if (findByEmail(normalized) != null) {
            throw new InvalidInputException("An account with this email already exists.");
        }
    }

    public User login(String email, String password) throws AuthenticationException {
        String e = email == null ? "" : email.trim().toLowerCase();
        String hash = PasswordUtil.hash(password == null ? "" : password);
        for (User u : users.values()) {
            if (u.getEmail().equals(e) && u.passwordMatches(hash)) {
                return u;
            }
        }
        throw new AuthenticationException("Incorrect email or password.");
    }

    public User findByEmail(String email) {
        for (User u : users.values()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        for (User u : users.values()) {
            if (u instanceof Student) {
                students.add((Student) u);
            }
        }
        return students;
    }

    @Override
    public void add(User user) throws DataStorageException {
        users.put(user.getId(), user);
        persist();
    }

    @Override
    public boolean remove(String id) throws DataStorageException {
        boolean removed = users.remove(id) != null;
        if (removed) {
            persist();
        }
        return removed;
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(users.values());
    }

    private void persist() throws DataStorageException {
        store.save(new ArrayList<User>(users.values()));
    }
}
