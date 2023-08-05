package java_pos_system;

import java.time.LocalDate;
import java.sql.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

public class Sales {

    public enum paymentType {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD
    }

    Receiver r = new Receiver();
    private final String salesID;
    private String customer;
    private LinkedHashMap<String, Integer> item;
    private Date salesDate;
    private double totalPrice;
    private int paymentMethod;

    private boolean checkSufficientItem(Map item, String itemID) throws SQLException {
        int amount = (int) item.get(itemID);
        Database data = new Database();
        data.runCommand("SELECT * FROM ITEM WHERE itemID = '" + itemID + "'");
        List<Integer> stocks = data.getInt("itemAmount");
        int stock = stocks.get(0);
        data.closeConnection();
        return stock >= amount;
    }

    Sales(String salesID, String customer) {
        this.salesID = salesID;
        this.customer = customer;
        this.item = new LinkedHashMap();
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

    public LinkedHashMap getItemOrdered() {
        return this.item;
    }

    public void setItemOrdered(Item item, int orderAmount) throws SQLException {
        LinkedHashMap<String, Integer> targetItem = new LinkedHashMap();
        targetItem.put(item.getItemID(), orderAmount);
        if (checkSufficientItem(targetItem, item.getItemID())) {
            this.item.putAll(targetItem);
        } else {
            //order too much, what will happen?
        }
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

    public void submitSales() throws SQLException {
        Database data = new Database();
        data.runCommand("INSERT INTO SALE... ");

        //WIP
    }

    public void salesInfoCheckAndSubmit() {

    }
}
