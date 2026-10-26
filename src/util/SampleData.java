package util;

import exception.InternshipSystemException;
import model.Company;
import model.Internship;
import service.ApplicationService;
import service.CompanyService;
import service.InternshipService;
import service.UserService;

/** Fills the system with demo data on the very first run so the demo is never empty. */
public final class SampleData {

    private SampleData() { }

    public static void loadIfEmpty(UserService users, CompanyService companies,
                                   InternshipService internships, ApplicationService applications)
            throws InternshipSystemException {
        if (!users.getAll().isEmpty()) {
            return;
        }
        users.registerAdmin("System Admin", "admin@demo.com", "admin123");

        users.registerStudent("Aarav Sharma", "aarav@demo.com", "pass123", "B.Tech Computer Engineering", "Java, SQL");
        users.registerStudent("Diya Patel", "diya@demo.com", "pass123", "B.Sc Information Technology", "HTML, CSS, Python");
        users.registerStudent("Rohan Mehta", "rohan@demo.com", "pass123", "BCA", "C, Java");

        Company technova = companies.addCompany("TechNova Solutions", "Software", "Mumbai");
        Company greenleaf = companies.addCompany("GreenLeaf Analytics", "Data Analytics", "Pune");
        users.registerCompanyRep("Neha Kulkarni", "hr@technova.com", "pass123", technova.getId());
        users.registerCompanyRep("Imran Shaikh", "hr@greenleaf.com", "pass123", greenleaf.getId());

        Internship first = internships.post(technova, "Java Developer Intern", "Mumbai",
                "Work on backend modules using Core Java and collections.", 12000, 3);
        internships.post(technova, "Web Development Intern", "Mumbai",
                "Build responsive pages with HTML, CSS and JavaScript.", 10000, 2);
        internships.post(technova, "Software Testing Intern", "Remote",
                "Write and run test cases for web applications.", 8000, 3);
        internships.post(greenleaf, "Data Analyst Intern", "Pune",
                "Clean datasets and build dashboards for clients.", 15000, 6);
        internships.post(greenleaf, "Python Automation Intern", "Remote",
                "Automate reporting tasks using Python scripts.", 9000, 4);

        applications.apply(users.findByEmail("aarav@demo.com").getId(), first.getId());
    }
}
