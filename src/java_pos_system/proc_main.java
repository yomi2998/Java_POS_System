package java_pos_system;
public class proc_main {
    static Account account = new Account();

    public static void startSession() {
        LoginPage loginPage = new LoginPage(account.getLoginInfo());
    }

    public static void main(String[] args) {
        startSession();
    }
    
}
