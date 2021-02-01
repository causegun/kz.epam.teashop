package entity;

public class CartItem extends Entity{

    private long id;
    private long productId;
    private long cartId;
    private String createdAt;
    private int quantity;
    private String productName;

    public CartItem() {
    }

    public CartItem(long productId, long cartId, String createdAt, int quantity) {
        this.productId = productId;
        this.cartId = cartId;
        this.createdAt = createdAt;
        this.quantity = quantity;
    }

    public CartItem(long id, long productId, long cartId, String createdAt, int quantity) {
        this.id = id;
        this.productId = productId;
        this.cartId = cartId;
        this.createdAt = createdAt;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
