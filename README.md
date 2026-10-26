# Internship Management System

**Group 6 - Java OOP Mini Project**
Team members: _<add names and roll numbers here>_

A Java web application that manages students, companies, internship postings, applications and selection status.
It runs on the web server built into Java (`com.sun.net.httpserver`), so **no external libraries, frameworks or database are needed**.
Data is stored in files using Java file handling.

## Features

| User | What they can do |
| --- | --- |
| Student | Register, log in, search internships by keyword and location, apply, track application status |
| Company representative | Register with company profile, post internships, close internships, view applicants, accept or reject |
| Administrator | View all users, companies, internships and applications; remove students and internships |

Other behaviour: duplicate applications are blocked, closed internships cannot be applied to, all input is validated, passwords are stored as SHA-256 hashes, and data survives a restart.

## How to run

**Requirement:** JDK 8 or newer (check with `javac -version`).

* **Windows:** double-click `run.bat` (or run it in Command Prompt)
* **Mac / Linux:** `sh run.sh`
* **Any system, manual:**
  ```
  javac -d out src/model/*.java src/exception/*.java src/service/*.java src/util/*.java src/web/*.java src/main/*.java
  java -cp out main.Main
  ```
* **IDE (IntelliJ / Eclipse / VS Code):** open the folder, mark `src` as the source root, run `main.Main`. Run it from the project folder so the `data/` folder is found.

Then open **http://localhost:8080** in a browser. To use another port: `run.bat 9090` or `sh run.sh 9090`.

Optional runnable JAR: `jar cfe InternshipManagementSystem.jar main.Main -C out .` then `java -jar InternshipManagementSystem.jar`.

### Demo accounts (created automatically on first run)

| Role | Email | Password |
| --- | --- | --- |
| Student | aarav@demo.com | pass123 |
| Student | diya@demo.com | pass123 |
| Company | hr@technova.com | pass123 |
| Company | hr@greenleaf.com | pass123 |
| Admin | admin@demo.com | admin123 |

To reset all data, stop the server and delete the `.dat` files in the `data/` folder.

## OOP concepts and where to find them

| Concept | Where |
| --- | --- |
| Classes and objects | `model/Student`, `Company`, `Internship`, `Application`, `Administrator` |
| Constructors | Every model class; `Company` has both a default and a parameterized constructor; `Student` calls `super(...)` |
| Encapsulation | Private fields with getters and validating setters in all model classes |
| Inheritance | `Student`, `CompanyRepresentative`, `Administrator` extend `model/User`; all exceptions extend `InternshipSystemException` |
| Abstraction | `abstract class User` with abstract `getRole()`, `getDashboardTitle()`, `getMenuLinks()` |
| Polymorphism (runtime) | `WebServer` and `Html` call `user.getMenuLinks()`, `getDashboardTitle()`, `describe()` on a `User` reference; each subclass answers differently |
| Polymorphism (compile time) | `InternshipService.search(keyword)` and `search(keyword, location)` (overloading) |
| Interfaces | `service/Searchable<T>`, `Manageable<T>`, `Applicable` |
| Exception handling | `exception` package (7 custom exceptions); try/catch in `WebServer`, `Main`, `DataStore`; try-with-resources for files |
| Collections | `ArrayList` and `LinkedHashMap` (a `HashMap`) in every service |
| File handling | `util/DataStore` reads and writes `data/*.dat` with object streams |
| Generics | `DataStore<T extends Serializable>`, `Searchable<T>`, `Manageable<T>` |
| Packages | `model`, `exception`, `service`, `util`, `web`, `main` |

## Project structure

```
InternshipManagementSystem/
├── src/
│   ├── model/       User, Student, CompanyRepresentative, Administrator, Company, Internship, Application, ApplicationStatus
│   ├── exception/   InternshipSystemException and 6 subclasses
│   ├── service/     Searchable, Manageable, Applicable, UserService, CompanyService, InternshipService, ApplicationService
│   ├── util/        DataStore, Validator, PasswordUtil, IdGenerator, SampleData
│   ├── web/         WebServer, Html
│   └── main/        Main
├── UML/             .puml sources and .png diagrams
├── data/            saved data files (created at runtime)
├── docs/            report and presentation
├── run.bat / run.sh
└── README.md
```

## UML diagrams (in the `UML` folder)

1. Class diagram - domain model
2. Class diagram - services, interfaces and web layer
3. Class diagram - exception hierarchy
4. Use case diagram
5. Sequence diagram - student applies for an internship
6. Activity diagram - application process

![Domain class diagram](UML/1-class-diagram-domain.png)

## Test cases

| # | Test | Expected result | Result |
| --- | --- | --- | --- |
| 1 | Log in with a wrong password | Error "Incorrect email or password." | Pass |
| 2 | Student logs in with valid credentials | Student dashboard opens | Pass |
| 3 | Search "java" | Only matching internships are listed | Pass |
| 4 | Filter by location "pune" | Only Pune internships are listed | Pass |
| 5 | Apply to an open internship | Application saved as PENDING | Pass |
| 6 | Apply to the same internship twice | DuplicateApplicationException message shown | Pass |
| 7 | Apply to a non-existent internship | InternshipNotFoundException message shown | Pass |
| 8 | Student opens the admin page | Redirected to dashboard with an error | Pass |
| 9 | Company posts internship with stipend "abc" | "Stipend must be a whole number." | Pass |
| 10 | Company posts internship with duration 99 | "Duration must be between 1 and 24." | Pass |
| 11 | Company accepts an applicant | Status changes to ACCEPTED | Pass |
| 12 | Company closes an internship | Status CLOSED, hidden from student search | Pass |
| 13 | Register with an invalid email | "Please enter a valid email address." | Pass |
| 14 | Register with an existing email | "An account with this email already exists." | Pass |
| 15 | Register with a 2-character password | "Password must be at least 4 characters long." | Pass |
| 16 | Register with HTML in the name | Shown as plain text, not executed | Pass |
| 17 | Admin removes a student | Student and their applications are deleted | Pass |
| 18 | Restart the server | All users, internships and applications are still there | Pass |

## Limitations and future scope

* Data is kept in files and loaded into memory; a relational database (for example MySQL or SQLite through JDBC) could replace `DataStore` for larger data.
* Runs on a single thread, which is enough for a demo but not for many users.
* Possible additions: resume upload, email notifications, interview scheduling, student profile editing, password reset.
