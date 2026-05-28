package menus;

import services.MemberService;
import models.Member;
import services.ClassService;
import models.ClassEntity;

import java.util.List;
import java.util.Scanner;

public class MemberMenu {
    private Scanner scanner;
    private MemberService memberService;
    private ClassService classService;

    public MemberMenu() {
        scanner = new Scanner(System.in);
        memberService = new MemberService();
        classService = new ClassService();
    }

    public void display() {
        System.out.println("\n--- Member Login ---");
        System.out.print("Enter your Member ID: ");
        int memberID;
        try {
            memberID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Member ID.");
            return;
        }

        Member member = memberService.getMemberByID(memberID);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.println("\nWelcome, " + member.getFirstName() + " " + member.getLastName() + "!");
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. View My Information");
            System.out.println("2. View Available Classes");
            System.out.println("3. Register for a Class");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewMemberInfo(member);
                    break;
                case "2":
                    viewAvailableClasses();
                    break;
                case "3":
                    registerForClass(member);
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

    // View Member Information
    private void viewMemberInfo(Member member) {
        System.out.println("\n--- Your Information ---");
        System.out.println(member);
    }

    // View Available Classes
    private void viewAvailableClasses() {
        System.out.println("\n--- Available Classes ---");
        List<ClassEntity> classes = classService.listAllClasses();
        if (classes.isEmpty()) {
            System.out.println("No classes available.");
        } else {
            for (ClassEntity gymClass : classes) {
                System.out.println(gymClass);
            }
        }
    }

    // Register for a Class
    private void registerForClass(Member member) {
        System.out.println("\n--- Register for a Class ---");
        System.out.print("Enter ClassID to register: ");
        int classID;
        try {
            classID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ClassID.");
            return;
        }

        ClassEntity gymClass = classService.getClassByID(classID);
        if (gymClass == null) {
            System.out.println("Class not found.");
            return;
        }

        boolean success = memberService.registerForClass(member.getMemberID(), classID);
        if (success) {
            System.out.println("Successfully registered for the class.");
        } else {
            System.out.println("Failed to register for the class.");
        }
    }
}
