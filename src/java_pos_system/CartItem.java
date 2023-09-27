package java_pos_system;

public class CartItem {

    private Product product = new Product();
    private int quantity;
    private double subtotal;
    private boolean reminder = false;
    private boolean selected = false;
    private boolean markDelete = false;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.getProductPrice() * quantity;
    }

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
        if (this.quantity > product.getProductQuantity()) {
            this.quantity = product.getProductQuantity();
            this.reminder = true;
        }
        this.subtotal = product.getProductPrice() * this.quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setProductID(String productID) {
        this.product.setProductID(productID);
        this.product.retrieveProductInfo();
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
}
