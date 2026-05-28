package menus;

import services.UserService;
import services.TrainerService;
import services.MemberService;
import models.User;
import models.Trainer;
import models.Member;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner;
    private UserService userService;
    private TrainerService trainerService;
    private MemberService memberService;

    public AdminMenu() {
        scanner = new Scanner(System.in);
        userService = new UserService();
        trainerService = new TrainerService();
        memberService = new MemberService();
    }

    public void display() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. Manage Employees");
            System.out.println("2. Manage Trainers");
            System.out.println("3. Manage Members");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageEmployees();
                    break;
                case "2":
                    manageTrainers();
                    break;
                case "3":
                    manageMembers();
                    break;
                case "4":
                    back = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Manage Employees
    private void manageEmployees() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Manage Employees ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    viewAllEmployees();
                    break;
                case "3":
                    updateEmployee();
                    break;
                case "4":
                    deleteEmployee();
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Add Employee
    private void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Role (Administrator/Employee): ");
        String role = scanner.nextLine().trim();

        if (!role.equalsIgnoreCase("Administrator") && !role.equalsIgnoreCase("Employee")) {
            System.out.println("Invalid role. Must be 'Administrator' or 'Employee'.");
            return;
        }

        User user = new User(username, password, role);
        boolean success = userService.registerUser(user);

        if (success) {
            System.out.println("Employee added successfully with UserID: " + user.getUserID());
        } else {
            System.out.println("Failed to add employee.");
        }
    }

    // View All Employees
    private void viewAllEmployees() {
        System.out.println("\n--- List of All Employees ---");
        List<User> employees = userService.getAllUsersByRole("Employee");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (User user : employees) {
                System.out.println(user);
            }
        }
    }

    // Update Employee
    private void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        System.out.print("Enter Employee UserID to update: ");
        int userID;
        try {
            userID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid UserID.");
            return;
        }

        User user = userService.getUserByID(userID);
        if (user == null || !user.getRole().equals("Employee")) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("New Username (leave blank to keep unchanged): ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) {
            user.setUsername(username);
        }

        System.out.print("New Password (leave blank to keep unchanged): ");
        String password = scanner.nextLine().trim();
        if (!password.isEmpty()) {
            // TODO: Implement password hashing
            user.setPassword(password);
        }

        boolean success = userService.updateUser(user);
        if (success) {
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Failed to update employee.");
        }
    }

    // Delete Employee
    private void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        System.out.print("Enter Employee UserID to delete: ");
        int userID;
        try {
            userID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid UserID.");
            return;
        }

        boolean success = userService.deleteUser(userID);
        if (success) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Failed to delete employee.");
        }
    }

    // Manage Trainers
    private void manageTrainers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Manage Trainers ---");
            System.out.println("1. Add Trainer");
            System.out.println("2. View All Trainers");
            System.out.println("3. Update Trainer");
            System.out.println("4. Delete Trainer");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addTrainer();
                    break;
                case "2":
                    viewAllTrainers();
                    break;
                case "3":
                    updateTrainer();
                    break;
                case "4":
                    deleteTrainer();
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Add Trainer
    private void addTrainer() {
        System.out.println("\n--- Add New Trainer ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Specialization: ");
        String specialization = scanner.nextLine().trim();

        Trainer trainer = new Trainer(firstName, lastName, email, specialization);
        boolean success = trainerService.addTrainer(trainer);

        if (success) {
            System.out.println("Trainer added successfully with TrainerID: " + trainer.getTrainerID());
        } else {
            System.out.println("Failed to add trainer.");
        }
    }

    // View All Trainers
    private void viewAllTrainers() {
        System.out.println("\n--- List of All Trainers ---");
        List<Trainer> trainers = trainerService.listAllTrainers();
        if (trainers.isEmpty()) {
            System.out.println("No trainers found.");
        } else {
            for (Trainer trainer : trainers) {
                System.out.println(trainer);
            }
        }
    }

    // Update Trainer
    private void updateTrainer() {
        System.out.println("\n--- Update Trainer ---");
        System.out.print("Enter TrainerID to update: ");
        int trainerID;
        try {
            trainerID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid TrainerID.");
            return;
        }

        Trainer trainer = trainerService.getTrainerByID(trainerID);
        if (trainer == null) {
            System.out.println("Trainer not found.");
            return;
        }

        System.out.print("New First Name (leave blank to keep unchanged): ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            trainer.setFirstName(firstName);
        }

        System.out.print("New Last Name (leave blank to keep unchanged): ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            trainer.setLastName(lastName);
        }

        System.out.print("New Email (leave blank to keep unchanged): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            trainer.setEmail(email);
        }

        System.out.print("New Specialization (leave blank to keep unchanged): ");
        String specialization = scanner.nextLine().trim();
        if (!specialization.isEmpty()) {
            trainer.setSpecialization(specialization);
        }

        boolean success = trainerService.updateTrainer(trainer);
        if (success) {
            System.out.println("Trainer updated successfully.");
        } else {
            System.out.println("Failed to update trainer.");
        }
    }

    // Delete Trainer
    private void deleteTrainer() {
        System.out.println("\n--- Delete Trainer ---");
        System.out.print("Enter TrainerID to delete: ");
        int trainerID;
        try {
            trainerID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid TrainerID.");
            return;
        }

        boolean success = trainerService.removeTrainer(trainerID);
        if (success) {
            System.out.println("Trainer deleted successfully.");
        } else {
            System.out.println("Failed to delete trainer.");
        }
    }

    // Manage Members
    private void manageMembers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Manage Members ---");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addMember();
                    break;
                case "2":
                    viewAllMembers();
                    break;
                case "3":
                    updateMember();
                    break;
                case "4":
                    deleteMember();
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Add Member
    private void addMember() {
        System.out.println("\n--- Add New Member ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Membership Type (PAYG/Open/Term): ");
        String membershipType = scanner.nextLine().trim().toUpperCase();

        System.out.print("Status (ACTIVE/INACTIVE): ");
        String status = scanner.nextLine().trim().toUpperCase();

        Member member = new Member(firstName, lastName, email, membershipType, status);
        boolean success = memberService.registerMember(member);

        if (success) {
            System.out.println("Member added successfully with MemberID: " + member.getMemberID());
        } else {
            System.out.println("Failed to add member.");
        }
    }

    // View All Members
    private void viewAllMembers() {
        System.out.println("\n--- List of All Members ---");
        List<Member> members = memberService.listAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }

    // Update Member
    private void updateMember() {
        System.out.println("\n--- Update Member ---");
        System.out.print("Enter MemberID to update: ");
        int memberID;
        try {
            memberID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid MemberID.");
            return;
        }

        Member member = memberService.getMemberByID(memberID);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("New First Name (leave blank to keep unchanged): ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            member.setFirstName(firstName);
        }

        System.out.print("New Last Name (leave blank to keep unchanged): ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            member.setLastName(lastName);
        }

        System.out.print("New Email (leave blank to keep unchanged): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            member.setEmail(email);
        }

        System.out.print("New Membership Type (PAYG/Open/Term) (leave blank to keep unchanged): ");
        String membershipType = scanner.nextLine().trim().toUpperCase();
        if (!membershipType.isEmpty()) {
            member.setMembershipType(membershipType);
        }

        System.out.print("New Status (ACTIVE/INACTIVE) (leave blank to keep unchanged): ");
        String status = scanner.nextLine().trim().toUpperCase();
        if (!status.isEmpty()) {
            member.setStatus(status);
        }

        boolean success = memberService.updateMember(member);
        if (success) {
            System.out.println("Member updated successfully.");
        } else {
            System.out.println("Failed to update member.");
        }
    }

    // Delete Member
    private void deleteMember() {
        System.out.println("\n--- Delete Member ---");
        System.out.print("Enter MemberID to delete: ");
        int memberID;
        try {
            memberID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid MemberID.");
            return;
        }

        boolean success = memberService.removeMember(memberID);
        if (success) {
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Failed to delete member.");
        }
    }
}
