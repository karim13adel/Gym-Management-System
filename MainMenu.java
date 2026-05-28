package menus;

import services.UserService;
import models.User;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    private UserService userService;

    public MainMenu() {
        scanner = new Scanner(System.in);
        userService = new UserService();
    }

    public void display() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPlease choose your role:");
            System.out.println("1. Administrator");
            System.out.println("2. Employee");
            System.out.println("3. Trainer");
            System.out.println("4. Member");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.display();
                    break;
                case "2":
                    EmployeeMenu employeeMenu = new EmployeeMenu();
                    employeeMenu.display();
                    break;
                case "3":
                    TrainerMenu trainerMenu = new TrainerMenu();
                    trainerMenu.display();
                    break;
                case "4":
                    MemberMenu memberMenu = new MemberMenu();
                    memberMenu.display();
                    break;
                case "5":
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
