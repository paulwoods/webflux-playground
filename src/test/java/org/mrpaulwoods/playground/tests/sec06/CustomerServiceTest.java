package org.mrpaulwoods.playground.tests.sec06;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec06.dto.CustomerDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec06")
public class CustomerServiceTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    public void allCustomers() {
        this.client.get()
                .uri("/customers")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDto.class)
                .value(list -> log.info("{}", list))
                .hasSize(10);
    }

    @Test
    public void paginatedCustomers() {
        this.client.get()
                .uri("/customers/paginated?page=3&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(5)
                .jsonPath("$[1].id").isEqualTo(6);
    }

    @Test
    public void customerById() {
        this.client.get()
                .uri("/customers/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    public void customerByIdNotFound() {
        this.client.get()
                .uri("/customers/12345")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Customer [id=12345] was not found");
    }

    @Test
    public void createAndDeleteCustomer() {

        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");

        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isNumber()
                .jsonPath("$.name").isEqualTo("marshal")
                .jsonPath("$.email").isEqualTo("marshal@gmail.com");

        this.client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }

    @Test
    public void createAndDeleteCustomerInvalidName() {

        var dto = new CustomerDto(null, null, "marshal@gmail.com");

        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Name is required");
    }

    @Test
    public void createAndDeleteCustomerInvalidEmail() {

        var dto = new CustomerDto(null, "marshal", null);

        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Valid email is required");
    }

    @Test
    public void updateCustomer() {

        var dto = new CustomerDto(null, "noel", "noel@gmail.com");

        this.client.put()
                .uri("/customers/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(10)
                .jsonPath("$.name").isEqualTo("noel")
                .jsonPath("$.email").isEqualTo("noel@gmail.com");
    }

    @Test
    public void updateCustomerNotFound() {

        var dto = new CustomerDto(null, "noel", "noel@gmail.com");

        this.client.put()
                .uri("/customers/12345")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Customer [id=12345] was not found");
    }

    @Test
    public void updateCustomerNameIsInvalid() {

        var dto = new CustomerDto(null, null, "noel@gmail.com");

        this.client.put()
                .uri("/customers/9")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Name is required");
    }

    @Test
    public void updateCustomerEmailIsInvalid() {

        var dto = new CustomerDto(null, "noel", null);

        this.client.put()
                .uri("/customers/9")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.detail").isEqualTo("Valid email is required");
    }

    @Test
    public void customerNotFound() {
        // get
        this.client.get()
                .uri("customers/999")
                .exchange()
                .expectStatus().isEqualTo(404);

        // delete
        this.client.delete()
                .uri("customers/999")
                .exchange()
                .expectStatus().isEqualTo(404);

        // put

        var dto = new CustomerDto(null, "noel", "noel@gmail.com");

        this.client.put()
                .uri("customers/999")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(404);
    }


}
