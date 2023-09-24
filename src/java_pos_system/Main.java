package java_pos_system;

import java.util.Scanner;

public class Main {
    private String userID;
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Welcome to POS System");
            System.out.println("---------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. About");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 4 || choice < 1)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    int choice2 = 0;
                    while (true) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Login as");
                        System.out.println("1. Member");
                        System.out.println("2. Staff");
                        System.out.println("3. Back");
                        System.out.print("Enter your choice: ");
                        try {
                            choice2 = Integer.parseInt(sc.nextLine());
                            if (choice2 > 3 || choice2 < 1)
                                throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Invalid choice, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            continue;
                        }
                        break;
                    }
                    switch (choice2) {
                        case 1 -> {
                            LoginMember loginMember = new LoginMember();
                            if (loginMember.performLoginOperation()) {
                                System.out.println("Login successful");
                            } else {
                                System.out.println("Login cancelled");
                            }
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                        case 2 -> {
                            LoginStaff loginStaff = new LoginStaff();
                            if (loginStaff.performLoginOperation()) {
                                System.out.println("Login successful");
                            } else {
                                System.out.println("Login cancelled");
                            }
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 2 -> {
                    int choice3 = 0;
                    while (true) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Register as");
                        System.out.println("1. Member");
                        System.out.println("2. Staff");
                        System.out.println("3. Back");
                        System.out.print("Enter your choice: ");
                        try {
                            choice3 = Integer.parseInt(sc.nextLine());
                            if (choice3 > 3 || choice3 < 1)
                                throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Invalid choice, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            continue;
                        }
                        break;
                    }
                    switch (choice3) {
                        case 1 -> {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            RegisterMember registerMember = new RegisterMember();
                            if (registerMember.performRegisterOperation()) {
                                System.out.println("Member registered successfully");
                            } else {
                                System.out.println("Cancelled member registration");
                            }
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                        case 2 -> {
                            RegisterStaff registerStaff = new RegisterStaff();
                            if (registerStaff.performRegisterOperation()) {
                                System.out.println("Staff registered successfully");
                            } else {
                                System.out.println("Cancelled staff registration");
                            }
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                        }
                    }
                }
                case 3 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("POS System is a system that sells phone accessories.");
                    System.out.println("It is developed by:");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                }
                case 4 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Thank you for using POS System.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    System.exit(0);
                }
            }
        }
    }
}
