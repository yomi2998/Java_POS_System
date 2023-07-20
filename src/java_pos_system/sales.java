package java_pos_system;

import java.util.ArrayDeque;
import java.util.Deque;

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
    private int paymentMethod;
}
