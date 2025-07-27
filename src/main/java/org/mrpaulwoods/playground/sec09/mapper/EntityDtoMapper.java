package org.mrpaulwoods.playground.sec09.mapper;

import org.mrpaulwoods.playground.sec09.dto.ProductDto;
import org.mrpaulwoods.playground.sec09.entity.Product;

public class EntityDtoMapper {

    public static Product toEntity(ProductDto dto) {
        return new Product(
                dto.getId(),
                dto.getDescription(),
                dto.getPrice()
        );
    }

    public static ProductDto toDto(Product entity) {
        return new ProductDto(
                entity.getId(),
                entity.getDescription(),
                entity.getPrice()
        );
    }

}
