package org.mrpaulwoods.playground.sec02.repository;

import org.mrpaulwoods.playground.sec02.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByDescription(String ipad);

    Flux<Product> findAllByPriceBetween(Integer lower, Integer upper);

    Flux<Product> findBy(Pageable pageable);

}
