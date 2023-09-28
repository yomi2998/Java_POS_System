package java_pos_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    private List<CartItem> addedItems = new ArrayList<CartItem>();
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

    public double getTotalSelected() {
        double total = 0;
        for (CartItem cartItem : addedItems) {
            if (cartItem.getSelected())
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

    public void loadCart() {
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

    public void printCartTable() {
        boolean hasReminder = false;
        boolean hasZeroQuantity = false;
        boolean hasSelection = false;
        boolean hasMarkDelete = false;
        Screen.cls();

        System.out.print("    ");
        for (int i = 0; i < 119; i++)
            System.out.print("-");
        System.out.println();
        System.out.printf("    |%-10s|%-23s|%-50s|%-10s|%-20s|\n", "No.", "Product Brand",
                "Product Name", "Quantity", "Subtotal");
        System.out.print("    ");
        for (int i = 0; i < 119; i++)
            System.out.print("-");
        System.out.println();
        int i = 1;
        for (CartItem cartItem : addedItems) {
            if (cartItem.getMarkDelete()) {
                System.out.printf("\u001B[41m    |%-10s|%-23s|%-50s|%-10s|RM %-17.2f|\n", i,
                        cartItem.getProduct().getProductBrand(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(), cartItem.getSubtotal());
                hasMarkDelete = true;
            } else if (cartItem.getSelected()) {
                System.out.printf("\u001B[42m    |%-10s|%-23s|%-50s|%-10s|RM %-17.2f|\n", i,
                        cartItem.getProduct().getProductBrand(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(), cartItem.getSubtotal());
                hasSelection = true;
            } else if (cartItem.getReminder()) {
                System.out.printf(
                        (cartItem.getQuantity() == 0 ? "\u001B[31m" : "")
                                + "    |%-10s|%-3s%-20s|%-50s|%-10s|RM %-17.2f|\n",
                        i,
                        cartItem.getReminder() ? "[!]" : "", cartItem.getProduct().getProductBrand(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(), cartItem.getSubtotal());
                hasReminder = cartItem.getReminder();
                if (cartItem.getQuantity() == 0) {
                    hasZeroQuantity = true;
                }
            } else {
                System.out.printf("\u001B[0m    |%-10s|%-23s|%-50s|%-10s|RM %-17.2f|\n", i,
                        cartItem.getProduct().getProductBrand(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(), cartItem.getSubtotal());
            }
            i++;
        }
        System.out.print("\u001B[0m    ");
        for (i = 0; i < 119; i++)
            System.out.print("-");
        System.out.println();
        if (!hasSelection)
            System.out.printf("    |%-10s|%-23s|%-50s|%-10s|RM %-17.2f|\n", "", "", "", "Total", getTotal());
        else
            System.out.printf("    |%-10s|%-23s|%-50s|\u001B[42m%-10s|RM %-17.2f\u001B[0m|\n", "", "", "", "Total",
                    getTotalSelected());
        System.out.print("    ");
        for (i = 0; i < 119; i++)
            System.out.print("-");
        System.out.println();
        if (hasReminder) {
            System.out.println("Product marked with [!] has been adjusted to the maximum quantity available.");
            if (hasZeroQuantity) {
                System.out.println(
                        "Product marked with \u001B[31mred color\u001B[0m are not available to purchase due to product shortage.");
            }
        }
        if (hasSelection) {
            System.out.println("Product marked with \u001B[42mgreen background\u001B[0m are selected to checkout.");
        }
        if (hasMarkDelete) {
            System.out.println("Product marked with \u001B[41mred background\u001B[0m are selected to remove.");
        }
        System.out.println();
    }

    public void removeSpecificItem() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            Screen.cls();
            Title.print("Remove item");
            printCartTable();
            System.out.println("Select items to delete, select again to remove selection.");
            try {
                System.out.print("Enter 1-" + addedItems.size()
                        + " from the table above to select (-1 to cancel, -2 to proceed removal): ");
                choice = Integer.parseInt(sc.nextLine());
                if (choice == -1)
                    return;
                else if (choice == -2)
                    break;
                --choice;
                if (choice < 0 || choice > addedItems.size())
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid option, please try again.");
                Screen.pause();
                continue;
            }
            boolean selected = addedItems.get(choice).getMarkDelete();
            addedItems.get(choice).setMarkDelete(!selected);
        }
        boolean hasMarkDelete = false;
        while (true) {
            for (CartItem cart : addedItems) {
                if (cart.getMarkDelete()) {
                    hasMarkDelete = true;
                    break;
                }
            }
            if (!hasMarkDelete) {
                System.out.println("No item selected to remove.");
                Screen.pause();
                break;
            }
            System.out.println("Are you sure you want to remove selected items? (Y/N): ");
            Scanner sc = new Scanner(System.in);
            try {
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    break;
                } else if (confirm.equalsIgnoreCase("N")) {
                    hasMarkDelete = false;
                    break;
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
            for (int i = 0; i < addedItems.size(); ++i) {
                if (hasMarkDelete && addedItems.get(i).getMarkDelete()) {
                    db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID() + "' AND productid = '"
                            + addedItems.get(i).getProduct().getProductID() + "'");
                    addedItems.remove(i);
                    --i;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            Screen.pause();
            return;
        }
        if (hasMarkDelete) {
            System.out.println("Item(s) removed successfully.");
            Screen.pause();
        }
    }

    public void clearCartOperation() {
        Screen.cls();
        Title.print("Clear cart");
        loadCart();
        if (getAddedItems().isEmpty()) {
            System.out.println("Your cart is empty, aborting clear cart operation.");
            Screen.pause();
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
            Screen.pause();
            return;
        }
        System.out.println("Cart cleared successfully.");
        Screen.pause();
    }

    public boolean checkout() {
        while (true) {
            Screen.cls();
            Title.print("Checkout");
            loadCart();
            if (getAddedItems().isEmpty()) {
                System.out.println("Your cart is empty, aborting clear cart operation.");
                Screen.pause();
                return false;
            }
            int choice = 0;
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("Your cart");
                printCartTable();
                System.out.println("1. Checkout all items");
                System.out.println("2. Select items to checkout");
                System.out.println("3. Cancel");
                System.out.print("Select option 1-3: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > 3)
                        throw new Exception();
                    if (choice == 3)
                        return false;
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid option, please try again.");
                    Screen.pause();
                    continue;
                }
            }
            switch (choice) {
                case 1 -> {
                    while (true) {
                        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
                        try {
                            Database db = new Database();
                            db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + getUserID() + "'");
                            if (db.hasResult()) {
                                while (db.next()) {
                                    PaymentMethod paymentMethod = new PaymentMethod();
                                    paymentMethod.setPaymentID(db.getString("paymentid"));
                                    paymentMethod.setPaymentMethod(db.getString("paymentmethod"));
                                    paymentMethod.setCardNumber(db.getString("cardnumber"));
                                    paymentMethods.add(paymentMethod);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            Screen.pause();
                            return false;
                        }
                        int paymentChoice = 0;
                        while (true) {
                            Screen.cls();

                            printCartTable();
                            System.out.println();
                            System.out.println("Select payment method");
                            System.out.println("1. Cash on delivery");
                            System.out.println("2. Balance");
                            for (int i = 0; i < paymentMethods.size(); i++) {
                                System.out.println((i + 3) + ". " + paymentMethods.get(i).getPaymentMethod() + " ("
                                        + paymentMethods.get(i).getCensoredCardNumber() + ")");
                            }
                            System.out.println((paymentMethods.size() + 3) + ". Add new payment method");
                            System.out.println((paymentMethods.size() + 4) + ". Cancel");
                            System.out.print("Select option 1-" + (paymentMethods.size() + 4) + ": ");
                            try {
                                paymentChoice = Integer.parseInt(sc.nextLine());
                                if (paymentChoice < 1 || paymentChoice > (paymentMethods.size() + 4))
                                    throw new Exception();
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid option, please try again.");
                                Screen.pause();
                                continue;
                            }
                        }
                        if (paymentChoice == (paymentMethods.size() + 3)) {
                            paymentChoice = 999;
                        } else if (paymentChoice == (paymentMethods.size() + 4)) {
                            break;
                        }
                        switch (paymentChoice) {
                            case 1 -> {
                                while (true) {
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity() + ", "
                                                            + addedItems.get(i).getSubtotal()
                                                            + ", 'Cash On Delivery', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                            case 2 -> {
                                while (true) {
                                    try {
                                        Database db = new Database();
                                        db.runCommand("SELECT memberbalance FROM member WHERE memberid = '"
                                                + getUserID() + "'");
                                        db.next();
                                        int balance = db.getInt("memberbalance");
                                        if (balance < getTotal()) {
                                            System.out.println("Insufficient balance, please top up your balance.");
                                            Screen.pause();
                                            return false;
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity() + ", "
                                                            + addedItems.get(i).getSubtotal()
                                                            + ", 'Balance', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand("UPDATE MEMBER SET memberbalance = memberbalance - "
                                                    + addedItems.get(i).getSubtotal() + " WHERE memberid = '"
                                                    + getUserID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                            case 999 -> {
                                PaymentMethod pm = new PaymentMethod(getUserID());
                                pm.startPaymentMethodSession();
                            }
                            default -> {
                                while (true) {
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, paymentid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + paymentMethods.get(paymentChoice - 3).getPaymentID()
                                                            + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity()
                                                            + ", '"
                                                            + addedItems.get(i).getSubtotal() + "', '"
                                                            + paymentMethods.get(paymentChoice - 3).getPaymentMethod()
                                                            + "', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                        }
                    }
                }
                case 2 -> {
                    while (true) {
                        Screen.cls();

                        printCartTable();
                        System.out.println("Select items to checkout, select again to remove selection.");
                        try {
                            System.out.print("Enter 1-" + addedItems.size()
                                    + " from the table above to select (-1 to cancel, -2 to proceed payment): ");
                            choice = Integer.parseInt(sc.nextLine());
                            if (choice == -1)
                                return false;
                            else if (choice == -2)
                                break;
                            --choice;
                            if (choice < 0 || choice > addedItems.size())
                                throw new Exception();
                            if (addedItems.get(choice).getQuantity() == 0) {
                                System.out.println("Product is not available to purchase due to product shortage.");
                                Screen.pause();
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid option, please try again.");
                            Screen.pause();
                            continue;
                        }
                        boolean selected = addedItems.get(choice).getSelected();
                        addedItems.get(choice).setSelected(!selected);
                    }
                    while (true) {
                        boolean hasSelection = false;
                        for (CartItem cart : addedItems) {
                            if (cart.getSelected()) {
                                hasSelection = true;
                                break;
                            }
                        }
                        if (!hasSelection) {
                            System.out.println("No item selected to checkout.");
                            Screen.pause();
                            break;
                        }
                        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
                        try {
                            Database db = new Database();
                            db.runCommand("SELECT * FROM payment_method WHERE memberid = '" + getUserID() + "'");
                            if (db.hasResult()) {
                                while (db.next()) {
                                    PaymentMethod paymentMethod = new PaymentMethod();
                                    paymentMethod.setPaymentID(db.getString("paymentid"));
                                    paymentMethod.setPaymentMethod(db.getString("paymentmethod"));
                                    paymentMethod.setCardNumber(db.getString("cardnumber"));
                                    paymentMethods.add(paymentMethod);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            Screen.pause();
                            return false;
                        }
                        int paymentChoice = 0;
                        while (true) {
                            Screen.cls();

                            printCartTable();
                            System.out.println();
                            System.out.println("Select payment method");
                            System.out.println("1. Cash on delivery");
                            System.out.println("2. Balance");
                            for (int i = 0; i < paymentMethods.size(); i++) {
                                System.out.println((i + 3) + ". " + paymentMethods.get(i).getPaymentMethod() + " ("
                                        + paymentMethods.get(i).getCensoredCardNumber() + ")");
                            }
                            System.out.println((paymentMethods.size() + 3) + ". Add new payment method");
                            System.out.println((paymentMethods.size() + 4) + ". Cancel");
                            System.out.print("Select option 1-" + (paymentMethods.size() + 4) + ": ");
                            try {
                                paymentChoice = Integer.parseInt(sc.nextLine());
                                if (paymentChoice < 1 || paymentChoice > (paymentMethods.size() + 4))
                                    throw new Exception();
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid option, please try again.");
                                Screen.pause();
                                continue;
                            }
                        }
                        if (paymentChoice == (paymentMethods.size() + 3)) {
                            paymentChoice = 999;
                        } else if (paymentChoice == (paymentMethods.size() + 4)) {
                            break;
                        }
                        switch (paymentChoice) {
                            case 1 -> {
                                while (true) {
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (!addedItems.get(i).getSelected())
                                                continue;
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity() + ", "
                                                            + addedItems.get(i).getSubtotal()
                                                            + ", 'Cash On Delivery', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                            case 2 -> {
                                while (true) {
                                    try {
                                        Database db = new Database();
                                        db.runCommand("SELECT memberbalance FROM member WHERE memberid = '"
                                                + getUserID() + "'");
                                        db.next();
                                        int balance = db.getInt("memberbalance");
                                        if (balance < getTotal()) {
                                            System.out.println("Insufficient balance, please top up your balance.");
                                            Screen.pause();
                                            return false;
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (!addedItems.get(i).getSelected())
                                                continue;
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity() + ", "
                                                            + addedItems.get(i).getSubtotal()
                                                            + ", 'Balance', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand("UPDATE MEMBER SET memberbalance = memberbalance - "
                                                    + addedItems.get(i).getSubtotal() + " WHERE memberid = '"
                                                    + getUserID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                            case 999 -> {
                                PaymentMethod pm = new PaymentMethod(getUserID());
                                pm.startPaymentMethodSession();
                            }
                            default -> {
                                while (true) {
                                    System.out.print("Are you sure you want to checkout all items? (Y/N): ");
                                    String confirm = sc.nextLine();
                                    if (confirm.equalsIgnoreCase("Y")) {
                                    } else if (confirm.equalsIgnoreCase("N")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option, please try again.");
                                        continue;
                                    }

                                    try {
                                        Database db = new Database();
                                        IDGenerator idGenerator = new IDGenerator("checkout");
                                        String checkoutID = idGenerator.getID("checkoutid");
                                        for (int i = 0; i < addedItems.size(); ++i) {
                                            if (!addedItems.get(i).getSelected())
                                                continue;
                                            if (addedItems.get(i).getQuantity() > addedItems.get(i).getProduct()
                                                    .getProductQuantity()) {
                                                System.out.println("Product "
                                                        + addedItems.get(i).getProduct().getProductName()
                                                        + " is not available to purchase due to product shortage.");
                                                continue;
                                            } else if (addedItems.get(i).getQuantity() == 0) {
                                                continue;
                                            }
                                            db.runCommand("UPDATE product SET productquantity = productquantity - "
                                                    + addedItems.get(i).getQuantity() + " WHERE productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                            db.runCommand(
                                                    "INSERT INTO checkout (checkoutid, memberid, paymentid, productid, productquantity, productsubtotal, paymentmethod, checkoutdatetime) VALUES ('"
                                                            + checkoutID + "', '" + getUserID() + "', '"
                                                            + paymentMethods.get(paymentChoice - 3).getPaymentID()
                                                            + "', '"
                                                            + addedItems.get(i).getProduct().getProductID() + "', "
                                                            + addedItems.get(i).getQuantity() + ", "
                                                            + addedItems.get(i).getSubtotal()
                                                            + ", '"
                                                            + paymentMethods.get(paymentChoice - 3).getPaymentMethod()
                                                            + "', NOW())");
                                            db.runCommand("DELETE FROM cart WHERE memberid = '" + getUserID()
                                                    + "' AND productid = '"
                                                    + addedItems.get(i).getProduct().getProductID() + "'");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        Screen.pause();
                                        return false;
                                    }
                                    System.out.println("Checkout successful.");
                                    Screen.pause();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean startCheckCartSession() {
        while (true) {
            Screen.cls();
            Title.print("View Cart");
            loadCart();
            if (getAddedItems().isEmpty()) {
                System.out.println("Your cart is empty, returning to member menu...");
                Screen.pause();
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
                Screen.pause();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    if (checkout()) {
                        return true;
                    }
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
