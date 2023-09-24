package java_pos_system;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
public class Product {
    private String productID;
    private String productBrand;
    private String productName;
    private String productCategory;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private String brands[] = { // phone accessories
        "Apple",
        "Samsung",
        "Xiaomi",
        "Huawei",
        "Oppo",
        "Vivo",
        "Realme",
        "Asus",
        "Lenovo",
        "Sony",
        "Pineng",
        "Remax",
        "JBL",
        "Razer",
        "GameSir",
        "FlyDigi",
        "iQOO",
        "Black Shark"
    };
    private String categories[] = {
        "Screen Protector",
        "Phone Case",
        "Phone Charger",
        "Phone Cable",
        "Power Bank",
        "Wired Earphone",
        "Wireless Earphone",
        "Wired Headphone",
        "Wireless Headphone",
        "Game Controller",
        "Phone Cooler",
        "Tempered Glass",
        "OTG Cable",
        "USB-C to 3.5mm Jack",
    };
    public Product() {
    }

    public Product(String productID) {
        setProductID(productID);
        retrieveProductInfo();
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void retrieveProductInfo() {
        Database db = new Database();
        try {
            db.runCommand("SELECT * FROM product WHERE productid = '" + getProductID() + "'");
            if (db.hasResult()) {
                setProductBrand(db.getString("productbrand"));
                setProductName(db.getString("productname"));
                setProductCategory(db.getString("productcategory"));
                setProductDescription(db.getString("productdescription"));
                setProductPrice(db.getDouble("productprice"));
                setProductQuantity(db.getInt("productquantity"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateProductInfo() {
        Database db = new Database();
        try {
            db.runCommand("UPDATE product SET productbrand = '" + getProductBrand() + "', productname = '"
                    + getProductName() + "', productcategory = '" + getProductCategory() + "', productdescription = '"
                    + getProductDescription() + "', productprice = " + getProductPrice() + ", productquantity = "
                    + getProductQuantity() + " WHERE productid = '" + getProductID() + "'");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean submitProduct() {
        Database db = new Database();
        try {
            db.runCommand("INSERT INTO product VALUES ('" + getProductID() + "', '" + getProductBrand() + "', '"
                    + getProductName() + "', '" + getProductPrice() + "', '" + getProductDescription() + "', "
                    + getProductCategory() + ", " + getProductQuantity() + ")");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean performRegisterProductOperation() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Register product");
        System.out.println("----------------");
        try {
            IDGenerator idGenerator = new IDGenerator("product");
            setProductID(idGenerator.getID("productid"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Product ID: " + getProductID());
        System.out.println("Select product brand: ");
        Arrays.sort(brands);
        for(int i = 0; i < brands.length; i++) {
            System.out.println((i + 1) + ". " + brands[i]);
        }
        System.out.print("Enter your choice: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(sc.nextLine());
            if (choice > brands.length || choice < 1)
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Invalid choice, please try again.");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return false;
        }
        setProductBrand(brands[choice - 1]);
        System.out.print("Enter product name: ");
        setProductName(sc.nextLine());
        System.out.println("Select product category: ");
        Arrays.sort(categories);
        for(int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Enter your choice: ");
        choice = 0;
        try {
            choice = Integer.parseInt(sc.nextLine());
            if (choice > categories.length || choice < 1)
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Invalid choice, please try again.");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return false;
        }
        setProductCategory(categories[choice - 1]);
        System.out.print("Enter product description: ");
        setProductDescription(sc.nextLine());
        System.out.print("Enter product price: ");
        try {
            setProductPrice(Double.parseDouble(sc.nextLine()));
            if(getProductPrice() < 0) throw new Exception();
        } catch (Exception e) {
            System.out.println("Invalid price, please try again.");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return false;
        }
        System.out.print("Enter product quantity: ");
        try {
            setProductQuantity(Integer.parseInt(sc.nextLine()));
            if(getProductQuantity() < 0) throw new Exception();
        } catch (Exception e) {
            System.out.println("Invalid quantity, please try again.");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return false;
        }
        if(submitProduct()) {
            System.out.println("Product registered successfully");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return true;
        } else {
            System.out.println("Product registration failed");
            System.out.print("Press enter to continue...");
            sc.nextLine();
            return false;
        }
    }
}
