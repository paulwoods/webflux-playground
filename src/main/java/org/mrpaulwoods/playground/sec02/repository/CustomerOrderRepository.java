package org.mrpaulwoods.playground.sec02.repository;

import org.mrpaulwoods.playground.sec02.entity.CustomerOrder;
import org.mrpaulwoods.playground.sec02.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {

    @Query("""
            SELECT
                p.*
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
            WHERE
                c.name = :name
            """
    )
    Flux<Product> getProductsOrderedByCustomer(String name);

}
