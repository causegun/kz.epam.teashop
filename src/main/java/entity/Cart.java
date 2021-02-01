package entity;

import java.math.BigDecimal;

public class Cart extends Entity{
    private long id;
    private long userId;
    private String createdAt;
    private BigDecimal totalPrice;

    public Cart() {
    }

    public Cart(long userId, String createdAt, BigDecimal totalPrice) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

    public Cart(long id, long userId, String createdAt, BigDecimal totalPrice) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
