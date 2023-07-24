package java_pos_system;

public class Item {

    Receiver receiver = new Receiver();
    private String itemID;
    private String itemName;
    private double itemPrice;
    private String itemDescription;
    private String itemSupplier;
    private int itemAvailable;
    private boolean forSale;
    private Date dateImported;

    Item(String itemID) {
        this.itemID = itemID;
    }

    public String getItemID() {
        return this.itemID;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName() {
        System.out.println("Setting item name for item " + this.itemID);
        this.itemName = receiver.getStr("Enter item name: ", 3, 50);
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public double getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice() {
        System.out.println("Setting item price for item " + this.itemID);
        this.itemPrice = receiver.getDouble("Enter price for the item: ", 0.0, 999999999.0);
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription() {
        System.out.println("Setting item description for item " + this.itemID);
        this.itemDescription = receiver.getStr("Enter item description: ", 0, 99999);
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public String getItemSupplier() {
        return this.itemSupplier;
    }

    public void setItemSupplier() {
        System.out.println("Setting item supplier for item " + this.itemID);
        this.itemSupplier = receiver.getStr("Enter supplier's name: ", 0, 100);
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public int getItemAmount() {
        return this.itemAvailable;
    }

    public void setItemAmount() {
        System.out.println("Setting item amount for item " + this.itemID);
        this.itemAvailable = receiver.getInt("Enter amount of item available: ", 0, 999999);
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public boolean getForSale() {
        return this.forSale;
    }

    public void setForSale() {
        System.out.println("Setting item for sale for item " + this.itemID);
        this.forSale = receiver.getYN("Item for sale? (Y/N): ");
        System.out.println("Details for item " + this.itemID + " has been set!");
    }

    public Date getImportDate() {
        return this.dateImported;
    }

    public void setImportDate() {
        System.out.println("Setting item import date for item " + this.itemID);
        this.dateImported = receiver.getDate("Enter import date (dd/mm/yyyy): ");
        System.out.println("Details for item " + this.itemID + " has been set!");
    }
}
