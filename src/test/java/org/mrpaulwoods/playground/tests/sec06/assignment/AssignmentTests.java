package org.mrpaulwoods.playground.tests.sec06.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest
public class AssignmentTests {

    @Autowired
    private WebTestClient client;

    @Test
    public void add() {
        this.client.get()
                .uri("/calculator/20/5")
                .header("operation", "+")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(25);
    }

    @Test
    public void subtract() {
        this.client.get()
                .uri("/calculator/20/5")
                .header("operation", "-")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(15);
    }

    @Test
    public void multiply() {
        this.client.get()
                .uri("/calculator/20/5")
                .header("operation", "*")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(100);
    }

    @Test
    public void divide() {
        this.client.get()
                .uri("/calculator/20/5")
                .header("operation", "/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(4);
    }

    @Test
    public void divide_by_zero_returns_bad_request() {
        this.client.get()
                .uri("/calculator/20/0")
                .header("operation", "/")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void missing_operation_header_returns_bad_request() {
        this.client.get()
                .uri("/calculator/20/5")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void invalid_operation_header_returns_bad_request() {
        this.client.get()
                .uri("/calculator/20/5")
                .header("operation", "XXXXX")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
