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

    public void setPlainQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setItemQuantity(int quantity) {
        setQuantity(quantity);
        if (getQuantity() > getProduct().getProductQuantity()) {
            setQuantity(getProduct().getProductQuantity());
            this.reminder = true;
        }
        setSubtotal(getProduct().getProductPrice() * getQuantity());
    }

    public void setItemQuantityPlain(int quantity) {
        setQuantity(quantity);
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
        getProduct().setProductID(productID);
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
    private boolean isStaff = false;
    List<List<ItemRecord>> itemsDatetimeSorted = new ArrayList<List<ItemRecord>>();

    ViewOrderedItems(String userID) {
        setUserID(userID);
    }

    ViewOrderedItems(String userID, boolean isStaff) {
        setUserID(userID);
        this.isStaff = isStaff;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public double getTotalPrice(int index) {
        double totalPrice = 0;
        for (ItemRecord eachItem : itemsDatetimeSorted.get(index)) {
            totalPrice += eachItem.getSubtotal();
        }
        return totalPrice;
    }

    public boolean retrieveOrderedItems() {
        try {
            itemsDatetimeSorted.clear();
            Database db = new Database();
            db.runCommand("SELECT * FROM checkout WHERE memberid = '" + getUserID() + "'");
            if (db.hasResult()) {
                List<ItemRecord> item = new ArrayList<ItemRecord>();
                String currentDateTime = "";
                while (db.next()) {
                    String thisDateTime = db.getString("checkoutdatetime");
                    if (currentDateTime.equals(""))
                        currentDateTime = thisDateTime;
                    if (!currentDateTime.equals(thisDateTime)) {
                        currentDateTime = thisDateTime;
                        itemsDatetimeSorted.add(item);
                        item = new ArrayList<ItemRecord>();
                    }
                    ItemRecord itemRecord = new ItemRecord();
                    itemRecord.setCheckoutID(db.getString("checkoutid"));
                    itemRecord.setCheckoutDatetime(db.getString("checkoutdatetime"));
                    itemRecord.setPaymentMethod(db.getString("paymentmethod"));
                    itemRecord.getProduct().setProductID(db.getString("productid"));
                    itemRecord.setPlainQuantity(db.getInt("productquantity"));
                    itemRecord.getProduct().retrieveProductInfo();
                    itemRecord.setSubtotal(db.getDouble("productsubtotal"));
                    itemRecord.getProduct().setProductPrice(db.getDouble("productsubtotal") / itemRecord.getQuantity());

                    item.add(itemRecord);
                }
                itemsDatetimeSorted.add(item);
            }
        } catch (Exception e) {
            System.out.println(e);
            Screen.pause();
            return false;
        }
        return true;
    }

    public void printSelectedTable(List<ItemRecord> item) {
        System.out.print("    ");
        for (int i = 0; i < 125; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-50s|%-50s|%-10s|%-10s|\n", "Product Brand", "Product Name", "Quantity", "Subtotal");
        System.out.print("    ");
        for (int i = 0; i < 125; i++)
            System.out.print("-");
        System.out.println();
        for (ItemRecord eachItem : item) {
            System.out.printf("    |%-50s|%-50s|%-10s|RM %-7.2f|\n", eachItem.getProduct().getProductBrand(),
                    eachItem.getProduct().getProductName(), eachItem.getQuantity(), eachItem.getSubtotal());
        }
        System.out.print("    ");
        for (int i = 0; i < 125; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-50s|%-50s|%-10s|RM %-7.2f|\n", "", "", "Total",
                getTotalPrice(itemsDatetimeSorted.indexOf(item)));
        System.out.print("    ");
        for (int i = 0; i < 125; i++)
            System.out.print("-");
        System.out.println();

        System.out.println("Payment method: " + item.get(0).getPaymentMethod());
        System.out.println("Order date: " + item.get(0).getCheckoutDatetime());
        if (!isStaff)
            Screen.pause();
    }

    public void startViewOrderSession() {
        while (true) {
            Screen.cls();
            Title.print("View Order History");
            retrieveOrderedItems();
            if (itemsDatetimeSorted.size() == 0) {
                System.out.println("No order history found.");
                Screen.pause();
                return;
            }
            System.out.println("Order history:");
            int i = 1;
            for (List<ItemRecord> eachDateItem : itemsDatetimeSorted) {
                System.out.printf("[" + i + "] " + eachDateItem.get(0).getCheckoutDatetime() + " (Total: RM %.2f)\n"
                        , getTotalPrice(i - 1));
                i++;
            }
            System.out.println("[0] Back");
            int choice = 0;
            try {
                Scanner s = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(s.nextLine());
                if (choice == 0)
                    return;
                else if (choice < 0 || choice > itemsDatetimeSorted.size())
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                Screen.pause();
                continue;
            }
            Screen.cls();
            System.out.println("Order history:");
            printSelectedTable(itemsDatetimeSorted.get(choice - 1));
            List<ItemRecord> selectedItem = itemsDatetimeSorted.get(choice - 1);
            if (!isStaff)
                continue;
            System.out.println("[1] Cancel order");
            System.out.println("[0] Back");
            while (true)
                try {
                    Scanner s = new Scanner(System.in);
                    System.out.print("Enter your choice: ");
                    choice = Integer.parseInt(s.nextLine());
                    if (choice < 0 || choice > 1)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input, please try again.");
                    Screen.pause();
                    continue;
                }
            if (choice == 0)
                continue;
            System.out.print("Are you sure you want to cancel this order? (Y/N): ");
            String confirm = "";
            while (true) {
                Scanner s = new Scanner(System.in);
                confirm = s.nextLine();
                if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N"))
                    break;
                System.out.print("Invalid input, please try again: ");
            }
            if (confirm.equalsIgnoreCase("Y")) {
                try {
                    Database db = new Database();
                    for(ItemRecord eachItem : selectedItem) {
                        db.runCommand("UPDATE product SET productquantity = productquantity + " + eachItem.getQuantity() + " WHERE productid = '" + eachItem.getProduct().getProductID() + "'");
                        db.runCommand("DELETE FROM checkout WHERE checkoutid = '" + eachItem.getCheckoutID() + "'");
                        if(eachItem.getPaymentMethod().equals("Balance")) {
                            db.runCommand("UPDATE member SET memberbalance = memberbalance + " + eachItem.getSubtotal() + " WHERE memberid = '" + getUserID() + "'");
                        }
                    }
                    System.out.println("Order cancelled.");
                    Screen.pause();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    Screen.pause();
                }
            }
        }
    }
}

class ManageOrderedItems extends Items {
    private String userID;
    List<List<ItemRecord>> itemsDatetimeSorted = new ArrayList<List<ItemRecord>>();

    ManageOrderedItems(String userID) {
        setUserID(userID);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean startManageOrderSession() {
        while (true) {
            Screen.cls();
            System.out.print("Enter member's ID to manage order (-1 to cancel): ");
            Scanner s = new Scanner(System.in);
            String memberID = s.nextLine();
            if (memberID.equals("-1"))
                return false;
            ViewOrderedItems viewOrderedItems = new ViewOrderedItems(memberID, true);
            viewOrderedItems.startViewOrderSession();
        }
    }
}