package java_pos_system;

public class Screen {
    public static void cls() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void pause() {
        try {
            System.out.println("\nPress enter to continue...");
            System.in.read();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
