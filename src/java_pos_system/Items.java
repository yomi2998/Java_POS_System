package java_pos_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Items {

    private Product product = new Product();
    private int quantity;
    private double subtotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = product.getProductPrice() * this.quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}

class CartItem extends Items {
    private boolean reminder = false;
    private boolean selected = false;
    private boolean markDelete = false;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
        setSubtotal(product.getProductPrice() * quantity);
    }

    @Override
    public void setQuantity(int quantity) {
        setQuantity(quantity);
        if (getQuantity() > getProduct().getProductQuantity()) {
            setQuantity(getProduct().getProductQuantity());
            this.reminder = true;
        }
        setSubtotal(getProduct().getProductPrice() * getQuantity());
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean getReminder() {
        return reminder;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setMarkDelete(boolean markDelete) {
        this.markDelete = markDelete;
    }

    public boolean getMarkDelete() {
        return markDelete;
    }

    public void setProductID(String productID) {
        setProductID(productID);
        getProduct().retrieveProductInfo();
    }
}

class ItemRecord extends Items {
    private String paymentMethod;
    private String checkoutID;
    private String checkoutDatetime;
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCheckoutID() {
        return checkoutID;
    }

    public void setCheckoutID(String checkoutID) {
        this.checkoutID = checkoutID;
    }

    public String getCheckoutDatetime() {
        return checkoutDatetime;
    }

    public void setCheckoutDatetime(String checkoutDatetime) {
        this.checkoutDatetime = checkoutDatetime;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

class ViewOrderedItems extends Items {
    private String userID;
    List<List<ItemRecord>> itemsDatetimeSorted = new ArrayList<List<ItemRecord>>();

    ViewOrderedItems(String userID) {
        setUserID(userID);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    private boolean retrieveOrderedItems() {
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM checkout WHERE userid = '" + getUserID() + "'");
            if (db.hasResult()) {
                List<ItemRecord> item = new ArrayList<ItemRecord>();
                String currentDateTime = "";
                while (db.next()) {
                    String thisDateTime = db.getString("checkoutdatetime");
                    if(!currentDateTime.equals(thisDateTime)) {
                        currentDateTime = thisDateTime;
                        itemsDatetimeSorted.add(item);
                        item = new ArrayList<ItemRecord>();
                    }
                    ItemRecord itemRecord = new ItemRecord();
                    itemRecord.setCheckoutID(db.getString("checkoutid"));
                    itemRecord.setCheckoutDatetime(db.getString("checkoutdatetime"));
                    itemRecord.setPaymentMethod(db.getString("paymentmethod"));
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    private void printSelectedTable(List<CartItem> item) {

    }

    public void startViewOrderSession() {
        while(true) {
            Screen.cls();
            retrieveOrderedItems();
            if(itemsDatetimeSorted.size() == 0) {
                System.out.println("You have no order history.");
                Screen.pause();
                break;
            }
        }
    }
}