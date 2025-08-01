package org.mrpaulwoods.playground.sec08.mapper;

import org.mrpaulwoods.playground.sec08.dto.ProductDto;
import org.mrpaulwoods.playground.sec08.entity.Product;

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
