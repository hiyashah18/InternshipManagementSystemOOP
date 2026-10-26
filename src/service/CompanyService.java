package service;

import exception.DataStorageException;
import exception.InternshipSystemException;
import model.Company;
import util.DataStore;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompanyService implements Manageable<Company> {

    private final Map<String, Company> companies = new LinkedHashMap<String, Company>();
    private final DataStore<Company> store;

    public CompanyService(DataStore<Company> store) throws DataStorageException {
        this.store = store;
        for (Company c : store.load()) {
            companies.put(c.getId(), c);
        }
    }

    public Company addCompany(String name, String industry, String location) throws InternshipSystemException {
        Company c = new Company(IdGenerator.next("COM"), name, industry, location);
        add(c);
        return c;
    }

    @Override
    public void add(Company company) throws DataStorageException {
        companies.put(company.getId(), company);
        persist();
    }

    @Override
    public boolean remove(String id) throws DataStorageException {
        boolean removed = companies.remove(id) != null;
        if (removed) {
            persist();
        }
        return removed;
    }

    @Override
    public Company findById(String id) {
        return companies.get(id);
    }

    @Override
    public List<Company> getAll() {
        return new ArrayList<Company>(companies.values());
    }

    private void persist() throws DataStorageException {
        store.save(new ArrayList<Company>(companies.values()));
    }
}
