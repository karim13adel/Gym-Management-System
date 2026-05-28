package application;

import menus.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Gym Management System ===");
        MainMenu mainMenu = new MainMenu();
        mainMenu.display();
    }
}
