package org.mrpaulwoods.playground.sec08.entity;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    private Integer id;
    private String description;
    private Integer price;

    public Product() {
    }

    public Product(Integer id, String description, Integer price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", price=" + price +
               '}';
    }
}
