package java_pos_system;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Message {
    private String message;
    private String time;
    private String name;
    private String id;

    public Message(String message, String time, String name, String id) {
        this.message = message;
        this.time = time;
        this.name = name;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class StaffSupport {
    List<List<Message>> messages = new ArrayList<List<Message>>();

    public StaffSupport() {
    }

    public boolean getRequest() {
        try {
            messages.clear();
            Database db = new Database();
            db.runCommand("SELECT * FROM support ORDER BY memberid AND date ASC");
            if (db.hasResult()) {
                List<Message> msg = new ArrayList<Message>();
                String id = "";
                while (db.next()) {
                    if (!id.equals(db.getString("memberid"))) {
                        if (!id.equals("")) {
                            messages.add(msg);
                            msg = new ArrayList<Message>();
                        }
                        id = db.getString("memberid");
                    }
                    String message = db.getString("problem");
                    String time = db.getString("date");
                    String name = db.getString("membername");
                    String mid = db.getString("memberid");
                    msg.add(new Message(message, time, name, mid));
                }
                messages.add(msg);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            Screen.pause();
            return false;
        }
    }

    public void startCheckMessageSession() {
        while (true) {
            Screen.cls();
            Title.print("Check member's request");
            System.out.println("");
            if (!getRequest()) {
                System.out.println("No request found.");
                Screen.pause();
                return;
            }
            for (int i = 0; i < messages.size(); i++) {
                System.out.println((i + 1) + ". " + messages.get(i).get(0).getName() + "(" + messages.get(i).get(0).getId() + ") - " + messages.get(i).get(0).getTime());
            }
            System.out.println((messages.size() + 1) + ". Back");
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
            if (choice == messages.size() + 1)
                return;
            else if (choice < 1 || choice > messages.size() + 1) {
                System.out.println("Invalid input, please try again.");
                Screen.pause();
                continue;
            }
            while (true) {
                Screen.cls();
                Title.print("Check member's request");
                System.out.println("");
                System.out.println("Member: " + messages.get(choice - 1).get(0).getName());
                System.out.println("Member ID: " + messages.get(choice - 1).get(0).getId());
                for (int i = 0; i < messages.get(choice - 1).size(); i++) {
                    System.out.println((i + 1) + ". " + messages.get(choice - 1).get(i).getMessage() + " - "
                            + messages.get(choice - 1).get(i).getTime());
                }
                System.out.println("");
                System.out.println("1. Mark as solved");
                System.out.println("2. Back");
                System.out.print("Enter choice: ");
                int choice2 = 0;
                try {
                    Scanner s = new Scanner(System.in);
                    choice2 = Integer.parseInt(s.nextLine());
                } catch (Exception e) {
                    System.out.println("Invalid input, please try again.");
                    Screen.pause();
                    continue;
                }
                if (choice2 == 2)
                    break;
                else if (choice2 == 1) {
                    while (true) {
                        System.out.println("Are you sure? (Y/N)");
                        Scanner s = new Scanner(System.in);
                        String choice3 = s.nextLine();
                        if (choice3.equalsIgnoreCase("Y")) {
                            try {
                                Database db = new Database();
                                db.runCommand("DELETE FROM support WHERE memberid = '"
                                        + messages.get(choice - 1).get(0).getId() + "'");
                                db.runCommand("INSERT INTO notification VALUES ('" + messages.get(choice - 1).get(0).getId() + "')");
                            } catch (Exception e) {
                                System.out.println(e);
                                Screen.pause();
                                return;
                            }
                        } else if (choice3.equalsIgnoreCase("N")) {
                            continue;
                        } else {
                            System.out.println("Invalid input, please try again.");
                            Screen.pause();
                            continue;
                        }
                        break;
                    }
                    System.out.println("Request marked as solved.");
                    Screen.pause();
                    break;
                }
            }
        }
    }
}
