package entity;

import java.math.BigDecimal;

public class Product implements Entity {

    private long id;
    private long languageId;
    private long categoryId;
    private String name;
    private String description = " ";
    private BigDecimal price;
    private String pathToPicture;

    public Product() {
    }

    public Product(long languageId, long categoryId, String name, String description, BigDecimal price, String pathToPicture) {
        this.languageId = languageId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToPicture = pathToPicture;
    }

    public Product(long id, long languageId, long categoryId, String name, String description, BigDecimal price, String pathToPicture) {
        this.id = id;
        this.languageId = languageId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToPicture = pathToPicture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToPicture() {
        return pathToPicture;
    }

    public void setPathToPicture(String pathToPicture) {
        this.pathToPicture = pathToPicture;
    }
}
