package java_pos_system;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String userID;
    private List<CartItem> addedItems = new ArrayList<CartItem>();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<CartItem> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(List<CartItem> addedItems) {
        this.addedItems = addedItems;
    }

    public Order(String memberID) {
        setUserID(memberID);
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        try {
            Database db = new Database();
            db.runCommand("SELECT * FROM product");
            if (db.hasResult())
                while (db.next()) {
                    Product product = new Product();
                    product.setProductID(db.getString("productid"));
                    product.setProductBrand(db.getString("productbrand"));
                    product.setProductCategory(db.getString("productcategory"));
                    product.setProductDescription(db.getString("productdescription"));
                    product.setProductQuantity(db.getInt("productquantity"));
                    product.setProductName(db.getString("productname"));
                    product.setProductPrice(db.getDouble("productprice"));
                    if (product.getProductQuantity() > 0)
                        products.add(product);
                }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return products;
    }

    private void printProducts(List<Product> products) {
        Screen.cls();
        
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
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%-3d.|%-10s|%-20s|%-50s|%-20s|RM %-12.2f|%-20d|\n", i + 1, product.getProductID(),
                    product.getProductBrand(), product.getProductName(), product.getProductCategory(),
                    product.getProductPrice(), product.getProductQuantity());
        }
        System.out.print("    ");
        for (int i = 0; i < 142; i++)
            System.out.print("-");
        System.out.println();
        System.out.println();
    }

    private void printSpecificProduct(Product product) {
        Screen.cls();
        
        System.out.println("Product ID: " + product.getProductID());
        System.out.println("Product Brand: " + product.getProductBrand());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Product Category: " + product.getProductCategory());
        System.out.println("Product Price: RM " + product.getProductPrice());
        System.out.println("Product Quantity: " + product.getProductQuantity());
        System.out.println("Product Description: " + product.getProductDescription());
    }

    public boolean startOrderItemSession() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            List<Product> products = getProducts();
            if (products == null) {
                System.out.println("Error retrieving products.");
                System.out.println("Press enter to continue...");
                sc.nextLine();
                return false;
            }
            printProducts(products);
            int productIndex = 0;
            while (true)
                try {
                    System.out.print("Select product 1-" + products.size() + " to add item to cart (-1 to cancel): ");
                    productIndex = Integer.parseInt(sc.nextLine());
                    if (productIndex == -1)
                        return false;
                    if (productIndex < 1 || productIndex > products.size())
                        throw new Exception();
                    productIndex--;
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input, please try again.");
                    continue;
                }
            printSpecificProduct(products.get(productIndex));
            int quantity = 0;
            while (true) {
                try {
                    System.out.print("Enter quantity to order (-1 to cancel): ");
                    quantity = Integer.parseInt(sc.nextLine());

                    if (quantity > products.get(productIndex).getProductQuantity()) {
                        System.out.println("Quantity ordered cannot be more than quantity available.");
                        continue;
                    } else if (quantity <= 0 && quantity != -1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input, please try again.");
                    continue;
                }
                break;
            }
            if (quantity == -1)
                continue;
            while (true) {
                System.out.print("Confirm add to cart? (Y/N): ");
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    CartItem cartItem = new CartItem(products.get(productIndex), quantity);
                    getAddedItems().add(cartItem);
                    try {
                        Database db = new Database();
                        db.runCommand("INSERT INTO cart VALUES ('" + getUserID() + "', '"
                                + cartItem.getProduct().getProductID() + "', " + cartItem.getQuantity() + ")");
                        System.out.println("Item added to cart.");
                        System.out.println("Press enter to continue...");
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("Error adding item to cart.");
                        System.out.println("Press enter to continue...");
                        sc.nextLine();
                        return false;
                    }
                    break;
                } else if (confirm.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Invalid input, please try again.");
                    continue;
                }
            }
        }
    }
}