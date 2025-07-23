package org.mrpaulwoods.playground.tests.sec07;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.tests.sec07.dto.CalculatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class Lec05ErrorResponseTest extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(Lec05ErrorResponseTest.class);

    private final WebClient client = createWebClient();

    @Test
    public void working() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "+")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void handleError1() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .onErrorReturn(new CalculatorResponse(0, 0, null, 0.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void handleError2() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)

                .onErrorReturn(WebClientResponseException.InternalServerError.class,
                        new CalculatorResponse(0, 0, null, -1.0))

                .onErrorReturn(WebClientResponseException.BadRequest.class,
                        new CalculatorResponse(0, 0, null, -2.0))

                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void handleError3() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)

                .doOnError(WebClientResponseException.class,
                        ex -> log.info("{}", ex.getResponseBodyAs(ProblemDetail.class)))

                .onErrorReturn(WebClientResponseException.InternalServerError.class,
                        new CalculatorResponse(0, 0, null, -1.0))

                .onErrorReturn(WebClientResponseException.BadRequest.class,
                        new CalculatorResponse(0, 0, null, -2.0))

                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
