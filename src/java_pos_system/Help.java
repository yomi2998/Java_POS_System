package java_pos_system;

import java.util.Scanner;

public class Help {
    String userID;
    String userName;

    public Help(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public void startHelpSession() {
        while (true) {
            Screen.cls();
            Title.print("Help Session");
            System.out.println("");
            System.out.println("1. Frequently Asked Questions");
            System.out.println("2. Contact Support");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int choice = 0;
            try {
                Scanner s = new Scanner(System.in);
                choice = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                Screen.pause();
                continue;
            }
            switch (choice) {
                case 1:
                    Screen.cls();
                    Title.print("Frequently Asked Questions");
                    System.out.println("");
                    System.out.println("Q: How do I cancel my order?");
                    System.out.println("A: You can cancel your order by contacting our staff.");
                    System.out.println("");
                    System.out.println("Q: How do I change my password?");
                    System.out.println("A: You can change your password by selecting edit profile in menu.");
                    System.out.println("");
                    System.out.println("Q: How do I know if my order is successful?");
                    System.out.println(
                            "A: If your order is shown in order history, that means your order is successful.");
                    Screen.pause();
                    break;
                case 2:
                    Screen.cls();
                    Title.print("Contact Support");
                    System.out.println("What's your problem? (-1 to cancel)");
                    Scanner s = new Scanner(System.in);
                    String problem = s.nextLine();
                    if (problem.equals("-1"))
                        break;
                    while (true) {
                        System.out.print("Confirm to send this problem? (Y/N) ");
                        String choice2 = s.nextLine();
                        if (choice2.equalsIgnoreCase("Y"))
                            break;
                        else if (choice2.equalsIgnoreCase("N"))
                            return;
                        else
                            System.out.println("Invalid input, please try again.");
                    }
                    try {
                        Database db = new Database();
                        db.runCommand(
                                "INSERT INTO support (memberid, membername, problem) VALUES ('" + userID + "', '" + userName + "', '" + problem + "')");
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("There is a problem with the database, please try again later.");
                        return;
                    }
                    System.out.println("Your problem has been sent to our staff.");
                    Screen.pause();
                    break;
                case 3:
                    return;
            }
        }
    }
}
