package java_pos_system;
public class Item {
    private String itemID;
    private String itemName;
    private double itemPrice;
    private String itemDescription;
    private String itemSupplier;
    private int itemAvailable;
    private char forSale;
    private Date date_imported;
    Item(String id, String name, double price, String desc, String supplier, int stock, char sale, Date date) {
        itemID = id;
        itemName = name;
        itemPrice = price;
        itemDescription = desc;
        itemSupplier = supplier;
        itemAvailable = stock;
        forSale = sale;
        date_imported = date;
    }
}
