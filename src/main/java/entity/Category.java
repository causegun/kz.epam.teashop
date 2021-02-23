package entity;

import java.util.ArrayList;
import java.util.List;

public class Category implements Entity {

    private long id;
    private long languageId;
    private String name;
    private List<Product> products = new ArrayList<>();


    public Category() {
    }

    public Category(long languageId, String name) {
        this.languageId = languageId;
        this.name = name;
    }

    public Category(long languageId, String name, List<Product> products) {
        this.languageId = languageId;
        this.name = name;
        this.products = products;
    }

    public Category(long id, long languageId, String name, List<Product> products) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
