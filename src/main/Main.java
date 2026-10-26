package main;

import exception.InternshipSystemException;
import model.Application;
import model.Company;
import model.Internship;
import model.User;
import service.ApplicationService;
import service.CompanyService;
import service.InternshipService;
import service.UserService;
import util.DataStore;
import util.SampleData;
import web.WebServer;

import java.io.IOException;

/** Entry point: loads saved data from the data/ folder, then starts the web server. */
public class Main {

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port '" + args[0] + "', using 8080.");
            }
        }

        try {
            // GENERICS: the same DataStore class is reused for four different record types
            UserService users = new UserService(new DataStore<User>("data/users.dat"));
            CompanyService companies = new CompanyService(new DataStore<Company>("data/companies.dat"));
            InternshipService internships = new InternshipService(new DataStore<Internship>("data/internships.dat"));
            ApplicationService applications =
                    new ApplicationService(new DataStore<Application>("data/applications.dat"), internships);

            SampleData.loadIfEmpty(users, companies, internships, applications);

            new WebServer(users, companies, internships, applications).start(port);
        } catch (InternshipSystemException e) {
            System.err.println("Startup failed: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Could not start the web server on port " + port + ": " + e.getMessage());
        }
    }
}
