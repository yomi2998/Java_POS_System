package java_pos_system;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
            "Black Shark",
            "Others"
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
            "Others"
    };

    public Product() {
    }

    public Product(String productID, String productBrand, String productName, String productCategory,
            String productDescription, double productPrice, int productQuantity) {
        setProductID(productID);
        setProductBrand(productBrand);
        setProductName(productName);
        setProductCategory(productCategory);
        setProductDescription(productDescription);
        setProductPrice(productPrice);
        setProductQuantity(productQuantity);
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

    public void displayProductInfo() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Product ID: " + getProductID());
        System.out.println("1. Product Brand: " + getProductBrand());
        System.out.println("2. Product Name: " + getProductName());
        System.out.println("3. Product Category: " + getProductCategory());
        System.out.println("4. Product Description: " + getProductDescription());
        System.out.printf("5. Product Price: RM %.2f\n", getProductPrice());
        System.out.println("6. Product Quantity: " + getProductQuantity());
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
                    + getProductName() + "', '" + getProductPrice() + "', '" + getProductDescription() + "', '"
                    + getProductCategory() + "', " + getProductQuantity() + ")");
            db.closeConnection();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Press enter to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return false;
        }
    }

    public void startViewProductSession() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Sort by:");
            System.out.println("1. Product ID");
            System.out.println("2. Product Brand");
            System.out.println("3. Product Name");
            System.out.println("4. Product Category");
            System.out.println("5. Product Price");
            System.out.println("6. Product Quantity");
            System.out.println("-1. Back");
            System.out.print("Enter your choice: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice > 6 || choice < -1)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            if (choice == -1)
                return;
            String cmd = "";
            switch (choice) {
                case 1 -> {
                    cmd = "SELECT * FROM product ORDER BY productid";
                }
                case 2 -> {
                    cmd = "SELECT * FROM product ORDER BY productbrand";
                }
                case 3 -> {
                    cmd = "SELECT * FROM product ORDER BY productname";
                }
                case 4 -> {
                    cmd = "SELECT * FROM product ORDER BY productcategory";
                }
                case 5 -> {
                    cmd = "SELECT * FROM product ORDER BY productprice";
                }
                case 6 -> {
                    cmd = "SELECT * FROM product ORDER BY productquantity";
                }
            }
            List<String> prods = new ArrayList<>();
            while (true) {
                try {
                    Database db = new Database();
                    db.runCommand(cmd);
                    if (db.hasResult()) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.print("    ");
                        for (int i = 0; i < 142; i++)
                            System.out.print("-");
                        System.out.println();
                        System.out.printf("    |%-10s|%-20s|%-50s|%-20s|%-15s|%-20s|\n", "Product ID", "Product Brand",
                                "Product Name", "Product Category", "Product Price", "Product Quantity");
                        System.out.print("    ");
                        for (int i = 0; i < 142; i++)
                            System.out.print("-");
                        System.out.println();
                        int count = 0;
                        while (db.next()) {
                            ++count;
                            System.out.printf("%-3d.|%-10s|%-20s|%-50s|%-20s|RM %-12.2f|%-20d|\n", count,
                                    db.getString("productid"),
                                    db.getString("productbrand"), db.getString("productname"),
                                    db.getString("productcategory"), db.getDouble("productprice"),
                                    db.getInt("productquantity"));
                            prods.add(String.format(
                                    "Product ID: %s\nProduct Brand: %s\nProduct Name: %s\nProduct Category: %s\nProduct Price: RM %.2f\nProduct Quantity: %d\nProduct Description: %s\n",
                                    db.getString("productid"),
                                    db.getString("productbrand"),
                                    db.getString("productname"),
                                    db.getString("productcategory"), db.getDouble("productprice"),
                                    db.getInt("productquantity"), db.getString("productdescription")));
                        }
                        System.out.print("    ");
                        for (int i = 0; i < 142; i++)
                            System.out.print("-");
                        System.out.println();
                        System.out.println();
                        System.out.print("Select product 1-" + count
                                + " to view full details (-1 to cancel, -2 to change order sequence): ");
                    } else {
                        System.out.println("No product found.");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        return;
                    }
                    db.closeConnection();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                choice = 0;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice > prods.size() || ((choice != -1 && choice != -2) && choice < 1))
                        throw new Exception();
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                if (choice == -1)
                    return;
                else if (choice == -2)
                    break;
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(prods.get(choice - 1));
                System.out.print("Press enter to continue...");
                sc.nextLine();
            }
        }
    }

    public boolean performEditProductOperation() {
        while (true) {
            List<Product> prods = new ArrayList<>();
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM product");
                if (db.hasResult()) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    System.out.printf("    |%-10s|%-20s|%-50s|%-20s|%-15s|%-20s|\n", "Product ID", "Product Brand",
                            "Product Name", "Product Category", "Product Price", "Product Quantity");
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    int count = 0;
                    while (db.next()) {
                        ++count;
                        System.out.printf("%-3d.|%-10s|%-20s|%-50s|%-20s|RM %-12.2f|%-20d|\n", count,
                                db.getString("productid"),
                                db.getString("productbrand"), db.getString("productname"),
                                db.getString("productcategory"), db.getDouble("productprice"),
                                db.getInt("productquantity"));
                        prods.add(new Product(db.getString("productid"), db.getString("productbrand"),
                                db.getString("productname"), db.getString("productcategory"),
                                db.getString("productdescription"), db.getDouble("productprice"),
                                db.getInt("productquantity")));
                    }
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    System.out.println();
                    System.out.print("Select product 1-" + count
                            + " to edit product details (-1 to cancel): ");
                } else {
                    System.out.println("No product found.");
                    return false;
                }
                db.closeConnection();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            int choice = 0;
            int selectedProd = 0;
            Scanner sc = new Scanner(System.in);
            try {
                selectedProd = Integer.parseInt(sc.nextLine());
                if (selectedProd > prods.size() || (selectedProd != -1 && selectedProd < 1))
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            if (selectedProd == -1)
                return false;
            while (true) {
                while (true) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    prods.get(selectedProd - 1).displayProductInfo();
                    System.out.print("Which field do you want to edit? (1-6, -1 to cancel): ");
                    try {
                        choice = Integer.parseInt(sc.nextLine());
                        if (choice == -1)
                            return false;
                        if (choice < 1 || choice > 6)
                            throw new Exception();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid choice, please try again.");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        continue;
                    }
                }
                switch (choice) {
                    case 1 -> {
                        System.out.println("Select product brand: ");
                        Arrays.sort(brands);
                        for (int i = 0; i < brands.length; i++) {
                            System.out.println((i + 1) + ". " + brands[i]);
                        }
                        System.out.println("-1. Cancel");
                        System.out.print("Enter your choice: ");
                        choice = 0;
                        try {
                            choice = Integer.parseInt(sc.nextLine());
                            if (choice == -1)
                                break;
                            if (choice > brands.length || choice < 1)
                                throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Invalid choice, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            break;
                        }
                        prods.get(selectedProd - 1).setProductBrand(brands[choice - 1]);
                    }
                    case 2 -> {
                        System.out.print("Enter product name (-1 to cancel): ");
                        prods.get(selectedProd - 1).setProductName(sc.nextLine());
                        if (prods.get(selectedProd - 1).getProductName().equals("-1"))
                            break;
                    }
                    case 3 -> {
                        System.out.println("Select product category: ");
                        Arrays.sort(categories);
                        for (int i = 0; i < categories.length; i++) {
                            System.out.println((i + 1) + ". " + categories[i]);
                        }
                        System.out.println("-1. Cancel");
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
                            break;
                        }
                        if (choice == -1)
                            break;
                        prods.get(selectedProd - 1).setProductCategory(categories[choice - 1]);
                    }
                    case 4 -> {
                        System.out.print("Enter product description: ");
                        prods.get(selectedProd - 1).setProductDescription(sc.nextLine());
                    }
                    case 5 -> {
                        while (true) {
                            System.out.print("Enter product price (-1 to cancel): RM ");
                            try {
                                double price = Double.parseDouble(sc.nextLine());
                                if (price == -1)
                                    break;
                                if (price < 0)
                                    throw new Exception();
                                prods.get(selectedProd - 1).setProductPrice(price);
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid price, please try again.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    case 6 -> {
                        while (true) {
                            System.out.print("Enter product quantity (-1 to cancel): ");
                            try {
                                int qty = Integer.parseInt(sc.nextLine());
                                if (qty == -1)
                                    break;
                                if (qty < 0)
                                    throw new Exception();
                                prods.get(selectedProd - 1).setProductQuantity(qty);
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid quantity, please try again.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                }
                while (true) {
                    prods.get(selectedProd - 1).displayProductInfo();
                    String confirm = "";
                    try {
                        System.out.print("Confirm product edit? (Y/N): ");
                        confirm = sc.nextLine();
                        if (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid choice, please try again.");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        continue;
                    }
                    if (confirm.toUpperCase().equals("Y")) {
                        return prods.get(selectedProd - 1).updateProductInfo();
                    }
                    while (true) {
                        System.out.print("Which field do you want to edit? (1-6, -1 to cancel): ");
                        try {
                            choice = Integer.parseInt(sc.nextLine());
                            if (choice == -1)
                                return false;
                            if (choice < 1 || choice > 6)
                                throw new Exception();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid choice, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            continue;
                        }
                    }
                    switch (choice) {
                        case 1 -> {
                            System.out.println("Select product brand: ");
                            Arrays.sort(brands);
                            for (int i = 0; i < brands.length; i++) {
                                System.out.println((i + 1) + ". " + brands[i]);
                            }
                            System.out.println("-1. Cancel");
                            System.out.print("Enter your choice: ");
                            choice = 0;
                            try {
                                choice = Integer.parseInt(sc.nextLine());
                                if (choice == -1)
                                    break;
                                if (choice > brands.length || choice < 1)
                                    throw new Exception();
                            } catch (Exception e) {
                                System.out.println("Invalid choice, please try again.");
                                System.out.print("Press enter to continue...");
                                sc.nextLine();
                                break;
                            }
                            prods.get(selectedProd - 1).setProductBrand(brands[choice - 1]);
                        }
                        case 2 -> {
                            System.out.print("Enter product name (-1 to cancel): ");
                            prods.get(selectedProd - 1).setProductName(sc.nextLine());
                            if (prods.get(selectedProd - 1).getProductName().equals("-1"))
                                break;
                        }
                        case 3 -> {
                            System.out.println("Select product category: ");
                            Arrays.sort(categories);
                            for (int i = 0; i < categories.length; i++) {
                                System.out.println((i + 1) + ". " + categories[i]);
                            }
                            System.out.println("-1. Cancel");
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
                                break;
                            }
                            if (choice == -1)
                                break;
                            prods.get(selectedProd - 1).setProductCategory(categories[choice - 1]);
                        }
                        case 4 -> {
                            System.out.print("Enter product description: ");
                            prods.get(selectedProd - 1).setProductDescription(sc.nextLine());
                        }
                        case 5 -> {
                            while (true) {
                                System.out.print("Enter product price (-1 to cancel): RM ");
                                try {
                                    double price = Double.parseDouble(sc.nextLine());
                                    if (price == -1)
                                        break;
                                    if (price < 0)
                                        throw new Exception();
                                    prods.get(selectedProd - 1).setProductPrice(price);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Invalid price, please try again.");
                                    System.out.print("Press enter to continue...");
                                    sc.nextLine();
                                    break;
                                }
                            }
                        }
                        case 6 -> {
                            while (true) {
                                System.out.print("Enter product quantity (-1 to cancel): ");
                                try {
                                    int qty = Integer.parseInt(sc.nextLine());
                                    if (qty == -1)
                                        break;
                                    if (qty < 0)
                                        throw new Exception();
                                    prods.get(selectedProd - 1).setProductQuantity(qty);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Invalid quantity, please try again.");
                                    System.out.print("Press enter to continue...");
                                    sc.nextLine();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
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
        for (int i = 0; i < brands.length; i++) {
            System.out.println((i + 1) + ". " + brands[i]);
        }
        System.out.println("-1. Cancel");
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
        if (choice == -1)
            return false;
        setProductBrand(brands[choice - 1]);
        System.out.print("Enter product name (-1 to cancel): ");
        setProductName(sc.nextLine());
        if (getProductName().equals("-1"))
            return false;
        System.out.println("Select product category: ");
        Arrays.sort(categories);
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.println("-1. Cancel");
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
        if (choice == -1)
            return false;
        setProductCategory(categories[choice - 1]);
        System.out.print("Enter product description: ");
        setProductDescription(sc.nextLine());
        while (true) {
            System.out.print("Enter product price (-1 to cancel): RM ");
            try {
                setProductPrice(Double.parseDouble(sc.nextLine()));
                if (getProductPrice() == -1)
                    return false;
                if (getProductPrice() < 0)
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Invalid price, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
        }
        while (true) {
            System.out.print("Enter product quantity (-1 to cancel): ");
            try {
                setProductQuantity(Integer.parseInt(sc.nextLine()));
                if (getProductQuantity() == -1)
                    return false;
                if (getProductQuantity() < 0)
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Invalid quantity, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
        }
        while (true) {
            displayProductInfo();
            String confirm = "";
            try {
                System.out.print("Confirm product submission? (Y/N): ");
                confirm = sc.nextLine();
                if (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            if (confirm.toUpperCase().equals("Y")) {
                return submitProduct();
            }
            while (true) {
                System.out.print("Which field do you want to edit? (1-6, -1 to cancel): ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice == -1)
                        return false;
                    if (choice < 1 || choice > 6)
                        throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("Select product brand: ");
                    Arrays.sort(brands);
                    for (int i = 0; i < brands.length; i++) {
                        System.out.println((i + 1) + ". " + brands[i]);
                    }
                    System.out.println("-1. Cancel");
                    System.out.print("Enter your choice: ");
                    choice = 0;
                    try {
                        choice = Integer.parseInt(sc.nextLine());
                        if (choice > brands.length || choice < 1)
                            throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Invalid choice, please try again.");
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        break;
                    }
                    if (choice == -1)
                        break;
                    setProductBrand(brands[choice - 1]);
                }
                case 2 -> {
                    System.out.print("Enter product name (-1 to cancel): ");
                    setProductName(sc.nextLine());
                    if (getProductName().equals("-1"))
                        break;
                }
                case 3 -> {
                    System.out.println("Select product category: ");
                    Arrays.sort(categories);
                    for (int i = 0; i < categories.length; i++) {
                        System.out.println((i + 1) + ". " + categories[i]);
                    }
                    System.out.println("-1. Cancel");
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
                        break;
                    }
                    if (choice == -1)
                        break;
                    setProductCategory(categories[choice - 1]);
                }
                case 4 -> {
                    System.out.print("Enter product description: ");
                    setProductDescription(sc.nextLine());
                }
                case 5 -> {
                    while (true) {
                        System.out.print("Enter product price (-1 to cancel): RM ");
                        try {
                            setProductPrice(Double.parseDouble(sc.nextLine()));
                            if (getProductPrice() == -1)
                                break;
                            if (getProductPrice() < 0)
                                throw new Exception();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid price, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            break;
                        }
                    }
                }
                case 6 -> {
                    while (true) {
                        System.out.print("Enter product quantity (-1 to cancel): ");
                        try {
                            setProductQuantity(Integer.parseInt(sc.nextLine()));
                            if (getProductQuantity() == -1)
                                break;
                            if (getProductQuantity() < 0)
                                throw new Exception();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid quantity, please try again.");
                            System.out.print("Press enter to continue...");
                            sc.nextLine();
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean performDeleteProductOperation() {
        while (true) {
            List<Product> prods = new ArrayList<>();
            try {
                Database db = new Database();
                db.runCommand("SELECT * FROM product");
                if (db.hasResult()) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    System.out.printf("    |%-10s|%-20s|%-50s|%-20s|%-15s|%-20s|\n", "Product ID", "Product Brand",
                            "Product Name", "Product Category", "Product Price", "Product Quantity");
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    int count = 0;
                    while (db.next()) {
                        ++count;
                        System.out.printf("%-3d.|%-10s|%-20s|%-50s|%-20s|RM %-12.2f|%-20d|\n", count,
                                db.getString("productid"),
                                db.getString("productbrand"), db.getString("productname"),
                                db.getString("productcategory"), db.getDouble("productprice"),
                                db.getInt("productquantity"));
                        prods.add(new Product(db.getString("productid"), db.getString("productbrand"),
                                db.getString("productname"), db.getString("productcategory"),
                                db.getString("productdescription"), db.getDouble("productprice"),
                                db.getInt("productquantity")));
                    }
                    System.out.print("    ");
                    for (int i = 0; i < 142; i++)
                        System.out.print("-");
                    System.out.println();
                    System.out.println();
                    System.out.print("Select product 1-" + count
                            + " to delete (-1 to cancel): ");
                } else {
                    System.out.println("No product found.");
                    return false;
                }
                db.closeConnection();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            int choice = 0;
            int selectedProd = 0;
            Scanner sc = new Scanner(System.in);
            try {
                selectedProd = Integer.parseInt(sc.nextLine());
                if (selectedProd > prods.size() || (selectedProd != -1 && selectedProd < 1))
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid choice, please try again.");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                continue;
            }
            if (selectedProd == -1)
                return false;
            while (true) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                prods.get(selectedProd - 1).displayProductInfo();
                String confirm = "";
                try {
                    System.out.print("Confirm product deletion? (Y/N): ");
                    confirm = sc.nextLine();
                    if (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid choice, please try again.");
                    System.out.print("Press enter to continue...");
                    sc.nextLine();
                    continue;
                }
                if (confirm.toUpperCase().equals("Y")) {
                    try {
                        Database db = new Database();
                        db.runCommand("DELETE FROM product WHERE productid = '"
                                + prods.get(selectedProd - 1).getProductID() + "'");
                        db.closeConnection();
                        return true;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.print("Press enter to continue...");
                        sc.nextLine();
                        return false;
                    }
                }
                break;
            }
        }
    }
}
