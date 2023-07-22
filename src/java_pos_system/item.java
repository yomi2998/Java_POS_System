package java_pos_system;
public class Item {
    private String itemID;
    private String itemName;
    private double itemPrice;
    private String itemDescription;
    private String itemSupplier;
    private int itemAvailable;
    private boolean forSale;
    private Date date_imported;
    Item(String itemID) {
        this.itemID = itemID;
    }
    public void setItemDetails() {
        Receiver receiver = new Receiver();
        System.out.println("Setting item details for item " + this.itemID);
        this.itemName = receiver.getStr("Enter item name: ", 3, 50);
        this.itemPrice = receiver.getDouble("Enter price for the item: ", 0.0, 999999999.0);
        this.itemDescription = receiver.getStr("Enter item description: ", 0, 99999);
        this.itemSupplier = receiver.getStr("Enter supplier's name: ", 0, 100);
        this.itemAvailable = receiver.getInt("Enter amount of item available: ", 0, 999999);
        this.forSale = receiver.getYN("Item for sale? (Y/N): ");
        this.date_imported = receiver.getDate("Enter import date (dd/mm/yyyy): ");
        System.out.println("Details for item " + this.itemID + " has been set!");
    }
}
