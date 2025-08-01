package org.mrpaulwoods.playground.sec09.dto;

@SuppressWarnings("unused")
public class ProductDto {

    private Integer id;
    private String description;
    private Integer price;

    public ProductDto() {
    }

    public ProductDto(Integer id, String description, Integer price) {
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
        return "ProductDto{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", price=" + price +
               '}';
    }

}
