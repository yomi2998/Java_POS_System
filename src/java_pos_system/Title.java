package java_pos_system;

public class Title {
    public static void print(String msg) {
        String msgs[] = msg.split("\n");
        int length = 0;
        for(String s : msgs) {
            if(s.length() > length)
                length = s.length();
        }
        for (int i = 0; i < length + 4; i++)
            System.out.print("-");
        System.out.println();
        for (String s : msgs) {
            System.out.print("| " + s);
            for (int i = 0; i < length - s.length(); i++)
                System.out.print(" ");
            System.out.println(" |");
        }
        for (int i = 0; i < length + 4; i++)
            System.out.print("-");
        System.out.println();
    }
}
