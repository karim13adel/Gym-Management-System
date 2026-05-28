# Gym Management System

A multi-role gym management console application built on a clean three-layer architecture: POJO domain models, JDBC data access objects, and a service layer, with a menu-driven CLI on top. Four actor types — Administrator, Employee, Trainer, and Member — each get a dedicated menu with role-appropriate operations. All state is persisted to a Microsoft SQL Server database. The architecture enforces strict separation of concerns: menus invoke services, services delegate to DAOs, and DAOs own all SQL. No menu or service class ever constructs a SQL string.

---

## System Architecture

**Models — `src/models/`**

Plain Java objects with no framework dependencies.

- `User` — authentication entity: `userID`, `username`, `passwordHash` (plaintext despite the name), `role` (Administrator or Employee)
- `Member` — gym member: `memberID`, `firstName`, `lastName`, `email`, `membershipType` (PAYG, Open, Term), `status` (ACTIVE or INACTIVE)
- `Trainer` — trainer profile: `trainerID`, `firstName`, `lastName`, `email`, `specialization`
- `ClassEntity` — gym class: `classID`, `className`, `schedule` (String), `trainerID` (foreign key)
- `ClassRegistration` — join entity: `registrationID`, `classID`, `memberID`, `registrationDate` (LocalDateTime)

**Data Access Layer — `src/dao/`**

Every DAO takes a shared `Connection` from `DatabaseConnection` in its constructor and executes all queries through `PreparedStatement`. Inserts use `Statement.RETURN_GENERATED_KEYS` to backfill the auto-incremented primary key into the passed model object immediately after insertion.

- `DatabaseConnection` — singleton pattern: the `getConnection()` static method returns a cached `Connection`, loading the `com.microsoft.sqlserver.jdbc.SQLServerDriver` class on first call and connecting to `jdbc:sqlserver://KARIM13ADEL:63675;databaseName=gym_management_systemm`.
- `MemberDAO` — full CRUD plus `getMembersByTrainerID(int trainerID)`, which executes a three-table JOIN across `Members`, `ClassRegistrations`, and `Classes` to retrieve all members enrolled in any class taught by a given trainer.
- `TrainerDAO` — full CRUD (add, get all, get by ID, update, delete).
- `ClassDAO` — full CRUD for gym classes.
- `ClassRegistrationDAO` — insert registration, check duplicate (`isMemberRegistered`), get registrations by member ID, get registrations by class ID, delete registration.
- `UserDAO` — full CRUD for authentication users, plus `getAllUsersByRole(String role)` and lookup by both ID and username.

**Service Layer — `src/services/`**

Thin business logic wrappers over DAOs. Services handle coordination and guard conditions; DAOs handle SQL.

- `MemberService` — wraps `MemberDAO` and delegates class enrollment to `ClassRegistrationService`.
- `TrainerService` — wraps `TrainerDAO`.
- `ClassService` — wraps `ClassDAO`.
- `ClassRegistrationService` — wraps `ClassRegistrationDAO`, enforces the duplicate-registration check before inserting.
- `UserService` — wraps `UserDAO`, used for authentication and user management from admin menus.

**Menu Layer — `src/menus/`**

Each menu class owns a `Scanner` and instantiates the services it needs. Navigation is loop-based — a `while (!back)` loop prints the menu, reads a string choice, and dispatches to a private method for each action.

- `MainMenu` — role selector: routes to AdminMenu, EmployeeMenu, TrainerMenu, or MemberMenu.
- `AdminMenu` — manages employees (via UserService), trainers (via TrainerService), and members (via MemberService). Each sub-section is a private method with its own nested loop.
- `EmployeeMenu` — manages members and class registrations.
- `TrainerMenu` — views assigned classes and the members enrolled in them.
- `MemberMenu` — views available classes, registers for a class, and views current registrations.

**Entry Point — `src/application/Main.java`**

Prints a welcome banner, constructs `MainMenu`, and calls `display()`. All initialization (DB connection, service instantiation) is deferred to the first menu interaction.

---

## Repository Structure

```
GymSystem/
├── lib/
│   └── mssql-jdbc-12.8.1.jre11.jar   # SQL Server JDBC driver (add to classpath)
├── src/
│   ├── application/
│   │   └── Main.java                  # Entry point
│   ├── models/
│   │   ├── User.java                  # Auth entity: username, passwordHash, role
│   │   ├── Member.java                # Member: name, email, membershipType, status
│   │   ├── Trainer.java               # Trainer: name, email, specialization
│   │   ├── ClassEntity.java           # Gym class: name, schedule, trainerID
│   │   └── ClassRegistration.java     # Join entity: classID, memberID, registrationDate
│   ├── dao/
│   │   ├── DatabaseConnection.java    # Singleton JDBC connection to SQL Server
│   │   ├── UserDAO.java               # CRUD for Users table + role-based lookup
│   │   ├── MemberDAO.java             # CRUD for Members + trainer-join query
│   │   ├── TrainerDAO.java            # CRUD for Trainers table
│   │   ├── ClassDAO.java              # CRUD for Classes table
│   │   └── ClassRegistrationDAO.java  # CRUD for ClassRegistrations + duplicate check
│   ├── services/
│   │   ├── UserService.java           # Delegates to UserDAO
│   │   ├── MemberService.java         # Delegates to MemberDAO + ClassRegistrationService
│   │   ├── TrainerService.java        # Delegates to TrainerDAO
│   │   ├── ClassService.java          # Delegates to ClassDAO
│   │   └── ClassRegistrationService.java  # Delegates to ClassRegistrationDAO with guard
│   └── menus/
│       ├── MainMenu.java              # Role selection
│       ├── AdminMenu.java             # Employee/Trainer/Member management
│       ├── EmployeeMenu.java          # Member + registration management
│       ├── TrainerMenu.java           # View classes and enrolled members
│       └── MemberMenu.java            # Browse classes, register, view registrations
```

---

## Quickstart

**Prerequisites:** Java 11+, Microsoft SQL Server (local or remote), IntelliJ IDEA or CLI with the JDBC JAR on the classpath.

1. Create the database and tables in SQL Server:
   ```sql
   CREATE DATABASE gym_management_system;
   USE gym_management_system;

   CREATE TABLE Users (
       UserID INT IDENTITY PRIMARY KEY,
       Username NVARCHAR(100) NOT NULL,
       PasswordHash NVARCHAR(255) NOT NULL,
       Role NVARCHAR(50) NOT NULL  -- 'Administrator' or 'Employee'
   );
   CREATE TABLE Members (
       MemberID INT IDENTITY PRIMARY KEY,
       FirstName NVARCHAR(100), LastName NVARCHAR(100),
       Email NVARCHAR(255), MembershipType NVARCHAR(50), Status NVARCHAR(50)
   );
   CREATE TABLE Trainers (
       TrainerID INT IDENTITY PRIMARY KEY,
       FirstName NVARCHAR(100), LastName NVARCHAR(100),
       Email NVARCHAR(255), Specialization NVARCHAR(100)
   );
   CREATE TABLE Classes (
       ClassID INT IDENTITY PRIMARY KEY,
       ClassName NVARCHAR(100), Schedule NVARCHAR(100), TrainerID INT
   );
   CREATE TABLE ClassRegistrations (
       RegistrationID INT IDENTITY PRIMARY KEY,
       ClassID INT, MemberID INT, RegistrationDate DATETIME
   );
   ```

2. Update the connection string in `DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:sqlserver://YOUR_SERVER:PORT;databaseName=gym_management_system;encrypt=false";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

3. Add the JDBC driver to the classpath and run:
   ```bash
   javac -cp lib/mssql-jdbc-12.8.1.jre11.jar -d out/production src/application/*.java src/models/*.java src/dao/*.java src/services/*.java src/menus/*.java
   java -cp out/production:lib/mssql-jdbc-12.8.1.jre11.jar application.Main
   # On Windows: use semicolons — out/production;lib/mssql-jdbc-12.8.1.jre11.jar
   ```

---

## Requirements

| Requirement | Version |
|---|---|
| Java | 11+ |
| Microsoft SQL Server | 2019+ |
| MSSQL JDBC Driver | 12.8.1 (included in `lib/`) |

---

## Stack

| Component | Technology |
|---|---|
| Language | Java |
| Database | Microsoft SQL Server |
| Database driver | mssql-jdbc 12.8.1.jre11 |
| Data access | Raw JDBC with PreparedStatement |
| UI | Console / Scanner |
| Build | IntelliJ IDEA module |

---

## Limitations

- **Singleton connection is not thread-safe.** `DatabaseConnection.getConnection()` caches a single `Connection` object. Concurrent access would require a connection pool (e.g., HikariCP).
- **Credentials hardcoded in source.** The server address, username, and password in `DatabaseConnection.java` must be externalized (environment variables or a config file) before pushing to a public repository.
- **No authentication at startup.** The main menu lets any user pick any role without entering credentials. `UserDAO` and `UserService` exist and support login queries but are not wired to a login gate.
- **Plaintext passwords.** The `passwordHash` field name implies hashing but the implementation stores and compares raw strings.
- **Database name has a typo.** The connection string targets `gym_management_systemm` (double m). Rename the database or fix the string before deployment.
