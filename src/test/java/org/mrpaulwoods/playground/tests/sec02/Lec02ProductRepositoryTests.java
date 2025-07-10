package org.mrpaulwoods.playground.tests.sec02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec02.repository.ProductRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec02ProductRepositoryTests extends AbstractTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec02ProductRepositoryTests.class);

    @Autowired
    private ProductRepository repository;

    @Test
    public void findAll() {
        this.repository.findAll()
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        this.repository.findById(2)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("iphone 18", p.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByName() {
        this.repository.findByDescription("ipad")
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(3, p.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findAlLByPriceRange() {
        this.repository.findAllByPriceBetween(200, 300)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("airpods pro", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("apple tv", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("homepod", p.getDescription()))
                .expectComplete()
                .verify();
    }

}
