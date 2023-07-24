package java_pos_system;

import java.util.Deque;
import java.time.LocalDate;

public class Sales {

    public enum paymentType {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD
    }
    Receiver r = new Receiver();
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

    public String getSalesID() {
        return this.salesID;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer() {
        System.out.println("Setting sales customer for " + this.salesID);
        this.customer = r.getStr("Enter customer's name: ", 2, 150);
    }

    public Deque<Item> getItemOrdered() {
        return this.item;
    }

    public void setItemOrdered() {
        // WIP
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public int getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod() {
        System.out.println("Setting sales payment method for " + this.salesID);
        this.paymentMethod = r.getInt("Enter payment method (0 = cash, 1 = credit card, 2 = debit card): ", 0, 2);
    }

    public Date getSalesDate() {
        return this.salesDate;
    }

    private boolean checkItem(Item item, int amount) {
        return !(amount > item.getItemAmount());
    }

    public void submitSales() {
        // WIP
    }
}
