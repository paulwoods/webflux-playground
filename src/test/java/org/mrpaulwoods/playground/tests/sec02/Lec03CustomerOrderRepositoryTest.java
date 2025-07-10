package org.mrpaulwoods.playground.tests.sec02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec02.repository.CustomerOrderRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec03CustomerOrderRepositoryTest extends AbstractTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec03CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository repository;

    @Test
    public void productsOrderedByCustomer() {
        this.repository.getProductsOrderedByCustomer("mike")
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("iphone 20", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("mac pro", p.getDescription()))
                .expectComplete()
                .verify();


    }

}
