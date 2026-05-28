package menus;

import services.TrainerService;
import services.MemberService;
import models.Trainer;
import models.Member;

import java.util.List;
import java.util.Scanner;

public class TrainerMenu {
    private Scanner scanner;
    private TrainerService trainerService;
    private MemberService memberService;

    public TrainerMenu() {
        scanner = new Scanner(System.in);
        trainerService = new TrainerService();
        memberService = new MemberService();
    }

    public void display() {
        System.out.println("\n--- Trainer Login ---");
        System.out.print("Enter your Trainer ID: ");
        int trainerID;
        try {
            trainerID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Trainer ID.");
            return;
        }

        Trainer trainer = trainerService.getTrainerByID(trainerID);
        if (trainer == null) {
            System.out.println("Trainer not found.");
            return;
        }

        System.out.println("\nWelcome, " + trainer.getFirstName() + " " + trainer.getLastName() + "!");
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Trainer Menu ---");
            System.out.println("1. View My Information");
            System.out.println("2. View My Classes");
            System.out.println("3. View My Members");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewTrainerInfo(trainer);
                    break;
                case "2":
                    viewTrainerClasses(trainer);
                    break;
                case "3":
                    viewTrainerMembers(trainer);
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

    // View Trainer Information
    private void viewTrainerInfo(Trainer trainer) {
        System.out.println("\n--- Your Information ---");
        System.out.println(trainer);
    }

    // View Trainer's Classes
    private void viewTrainerClasses(Trainer trainer) {
        System.out.println("\n--- Your Classes ---");
        // Implement functionality to view classes assigned to this trainer
        // This requires creating ClassDAO and ClassService
        // For simplicity, we'll assume such services exist
        // Placeholder:
        System.out.println("Feature to view assigned classes is under development.");
    }

    // View Trainer's Members
    private void viewTrainerMembers(Trainer trainer) {
        System.out.println("\n--- Your Members ---");
        List<Member> members = memberService.getMembersByTrainerID(trainer.getTrainerID());
        if (members.isEmpty()) {
            System.out.println("No members are assigned to you.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }
}
