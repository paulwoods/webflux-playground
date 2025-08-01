package org.mrpaulwoods.playground.tests.sec02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec02.repository.ProductRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class Lec02ProductRepositoryTests extends AbstractTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec02ProductRepositoryTests.class);

    @Autowired
    private ProductRepository repository;

    @Test
    public void findAll() {
        this.repository.findAll()
                .doOnNext(p -> log.info("{}", p))
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
    public void findAllByPriceRange() {
        this.repository.findAllByPriceBetween(200, 300)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("airpods pro", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("apple tv", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("homepod", p.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    public void pageable() {
        this.repository.findBy(PageRequest.of(0, 3).withSort(Sort.by("price").ascending()))
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();
    }

    @Test
    public void pageable2ndPage() {
        this.repository.findBy(PageRequest.of(1, 3).withSort(Sort.by("price").ascending()))
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(400, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(750, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(800, p.getPrice()))
                .expectComplete()
                .verify();
    }

}
/*
INSERT INTO product(description, price)
VALUES ('iphone 20', 1000),
       ('iphone 18', 750),
       ('ipad', 800),
       ('mac pro', 3000),
       ('apple watch', 400),
       ('macbook air', 1200),
       ('airpods pro', 250),
       ('imac', 2000),
       ('apple tv', 200),
       ('homepod', 300);

 */