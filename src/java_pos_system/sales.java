package java_pos_system;
import java.util.Deque;
import java.time.LocalDate;
public class Sales {
    public enum paymentType {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD
    }
    private String salesID;
    private String customer;
    private Deque<Item> item;
    private Date salesDate;
    private double totalPrice;
    private int paymentMethod;
    Sales(String salesID, String customer) {
        this.salesID = salesID;
        this.customer = customer;
        LocalDate now = LocalDate.now();
        this.salesDate = new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
        this.totalPrice = 0.0;
        this.paymentMethod = -1;
    }
}
