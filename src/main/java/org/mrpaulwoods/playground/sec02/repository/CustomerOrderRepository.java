package org.mrpaulwoods.playground.sec02.repository;

import org.mrpaulwoods.playground.sec02.entity.CustomerOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {
}
