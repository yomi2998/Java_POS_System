package java_pos_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    private List<CartItem> addedItems = new ArrayList<CartItem>();
    private List<CartItem> selectedItems = new ArrayList<CartItem>();
    private String userID;

    public List<CartItem> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(List<CartItem> addedItems) {
        this.addedItems = addedItems;
    }

    public void addCartItem(CartItem cartItem) {
        addedItems.add(cartItem);
    }

    public void removeCartItem(int index) {
        addedItems.remove(index);
    }

    public void removeCartItem(CartItem cartItem) {
        addedItems.remove(cartItem);
    }

    public void clearCart() {
        addedItems.clear();
    }

    public double getTotal() {
        double total = 0;
        for (CartItem cartItem : addedItems) {
            total += cartItem.getSubtotal();
        }
        return total;
    }

    private void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public Cart(String memberID) {
        setUserID(memberID);
    }

    private void loadCart() {
        try {
            clearCart();
            Database db = new Database();
            db.runCommand("SELECT * FROM cart WHERE memberid = '" + getUserID() + "'");
            if (db.hasResult())
                while (db.next()) {
                    CartItem cartItem = new CartItem();
                    cartItem.setProductID(db.getString("productid"));
                    cartItem.setQuantity(db.getInt("productquantity"));
                    addCartItem(cartItem);
                }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void printSpecificCartItem(CartItem cartItem) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Product ID: " + cartItem.getProduct().getProductID());
        System.out.println("Product Brand: " + cartItem.getProduct().getProductBrand());
        System.out.println("Product Name: " + cartItem.getProduct().getProductName());
        System.out.println("Product Category: " + cartItem.getProduct().getProductCategory());
        System.out.printf("Product Price: RM %.2f\n", cartItem.getProduct().getProductPrice());
        System.out.println("Product Quantity: " + cartItem.getProduct().getProductQuantity());
        System.out.println("Quantity: " + cartItem.getQuantity());
        System.out.printf("Subtotal: RM %.2f\n", cartItem.getSubtotal());
    }

    private void printCartTable() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("    ");
        for (int i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|%-20s|\n", "No.", "Product ID", "Product Brand",
                "Product Name", "Quantity", "Subtotal");
        System.out.print("    ");
        for (int i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        int i = 1;
        for (CartItem cartItem : addedItems) {
            System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|RM %-17s|\n", i, cartItem.getProduct().getProductID(),
                    cartItem.getProduct().getProductBrand(), cartItem.getProduct().getProductName(),
                    cartItem.getQuantity(), cartItem.getSubtotal());
            i++;
        }
        System.out.print("    ");
        for (i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|RM %-17s|\n", "", "", "", "", "Total", getTotal());
        System.out.print("    ");
        for (i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
    }

    private void printSelectedItemTable() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("    ");
        for (int i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|%-20s|\n", "No.", "Product ID", "Product Brand",
                "Product Name", "Quantity", "Subtotal");
        System.out.print("    ");
        for (int i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        int i = 1;
        for (CartItem cartItem : selectedItems) {
            System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|RM %-17s|\n", i, cartItem.getProduct().getProductID(),
                    cartItem.getProduct().getProductBrand(), cartItem.getProduct().getProductName(),
                    cartItem.getQuantity(), cartItem.getSubtotal());
            i++;
        }
        System.out.print("    ");
        for (i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-10s|%-20s|%-20s|%-20s|%-10s|RM %-17s|\n", "", "", "", "", "Total", getTotal());
        System.out.print("    ");
        for (i = 0; i < 107; i++)
            System.out.print("-");
        System.out.println();
    }

    private void removeSpecificItem() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            loadCart();
            if (getAddedItems().isEmpty()) {
                System.out.println("Your cart is empty, aborting remove item operation.");
                System.out.print("Press enter to continue...");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                return;
            }
            System.out.println("Your cart");
            printCartTable();
            System.out.print("Enter 1-" + addedItems.size() + " from the table above to remove (-1 to cancel): ");
            int choice = 0;
            Scanner sc = new Scanner(System.in);
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == -1)
                    return;
                if (choice < 1 || choice > addedItems.size())
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid option, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            --choice;
            while (true)
                try {
                    System.out.print("Are you sure you want to remove this item? (Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        break;
                    } else if (confirm.equalsIgnoreCase("N")) {
                        return;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid option, please try again.");
                    continue;
                }
            try {
                Database db = new Database();
                db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID() + "' AND productid = '"
                        + addedItems.get(choice).getProduct().getProductID() + "'");
                removeCartItem(choice);
            } catch (Exception e) {
                System.out.println(e);
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            System.out.println("Item removed successfully.");
            System.out.print("Press enter to continue...");
            sc.nextLine();
        }
    }

    private void clearCartOperation() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        loadCart();
        if (getAddedItems().isEmpty()) {
            System.out.println("Your cart is empty, aborting clear cart operation.");
            System.out.print("Press enter to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return;
        }
        System.out.println("Your cart");
        printCartTable();
        while (true) {
            try {
                System.out.print("Are you sure you want to clear your cart? (Y/N): ");
                Scanner sc = new Scanner(System.in);
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    break;
                } else if (confirm.equalsIgnoreCase("N")) {
                    return;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid option, please try again.");
                continue;
            }
        }
        try {
            Database db = new Database();
            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID() + "'");
            clearCart();
        } catch (Exception e) {
            System.out.println(e);
            System.out.print("Press enter to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return;
        }
        System.out.println("Cart cleared successfully.");
        System.out.print("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    private void checkout() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            loadCart();
            if (getAddedItems().isEmpty()) {
                System.out.println("Your cart is empty, aborting clear cart operation.");
                System.out.print("Press enter to continue...");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                return;
            }
            System.out.println("Your cart");
            printCartTable();
            System.out.println("1. Checkout all items");
            System.out.println("2. Select items to checkout");
            System.out.println("3. Cancel");
            System.out.print("Select option 1-3: ");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > 3)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid option, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
            }
            switch(choice) {
                case 1 -> {
                    //need modify, select payment method...
                    try {
                        Database db = new Database();
                        db.runCommand("SELECT * FROM cart WHERE memberid = '" + getUserID() + "'");
                        if (!db.hasResult()) {
                            System.out.println("Error retrieving cart items.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            return;
                        }
                        while (db.next()) {
                            db.runCommand("SELECT * FROM product WHERE productid = '" + db.getString("productid") + "'");
                            if (!db.hasResult()) {
                                System.out.println("Error retrieving product info.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                return;
                            }
                            db.next();
                            if (db.getInt("productquantity") < db.getInt("productquantity")) {
                                System.out.println("Product quantity is not enough.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                return;
                            }
                        }
                        db.runCommand("SELECT * FROM cart WHERE memberid = '" + getUserID() + "'");
                        if (!db.hasResult()) {
                            System.out.println("Error retrieving cart items.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            return;
                        }
                        while (db.next()) {
                            db.runCommand("SELECT * FROM product WHERE productid = '" + db.getString("productid") + "'");
                            if (!db.hasResult()) {
                                System.out.println("Error retrieving product info.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                return;
                            }
                            db.next();
                            db.runCommand("UPDATE product SET productquantity = productquantity - " + db.getInt("productquantity") + " WHERE productid = '" + db.getString("productid") + "'");
                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID() + "' AND productid = '" + db.getString("productid") + "'");
                        }
                        System.out.println("Checkout successful.");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        return;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        return;
                    }
                }
            }
        }
    }

    public boolean startCheckCartSession() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            loadCart();
            if (getAddedItems().isEmpty()) {
                System.out.println("Your cart is empty, returning to member menu...");
                System.out.print("Press enter to continue...");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                return false;
            }
            System.out.println("Your cart");
            printCartTable();
            System.out.println("1. Checkout");
            System.out.println("2. Remove item");
            System.out.println("3. Clear cart");
            System.out.println("4. Cancel");
            System.out.print("Select option 1-4: ");
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 1 || choice > 4)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid option, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1 -> {

                }
                case 2 -> {
                    removeSpecificItem();
                }
                case 3 -> {
                    clearCartOperation();
                }
                case 4 -> {
                    return false;
                }
            }
        }
    }
}
