package org.mrpaulwoods.aggregator.client;

import org.mrpaulwoods.aggregator.dto.CustomerInformation;
import org.mrpaulwoods.aggregator.dto.StockTradeRequest;
import org.mrpaulwoods.aggregator.dto.StockTradeResponse;
import org.mrpaulwoods.aggregator.exceptions.ApplicationExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import static org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;

public class CustomerServiceClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceClient.class);
    private final WebClient client;

    public CustomerServiceClient(WebClient client) {
        this.client = client;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return this.client.get()
                .uri("/customers/{customerId}", customerId)
                .retrieve()
                .bodyToMono(CustomerInformation.class)
                .onErrorResume(NotFound.class, ex -> ApplicationExceptions.customerNotFound(customerId));
    }

    public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request) {
        return this.client.post()
                .uri("/customers/{customerId}/trade", customerId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StockTradeResponse.class)
                .onErrorResume(NotFound.class, ex -> ApplicationExceptions.customerNotFound(customerId))
                .onErrorResume(BadRequest.class, this::handleException);
    }

    private <T> Mono<T> handleException(BadRequest exception) {
        var pd = exception.getResponseBodyAs(ProblemDetail.class);
        log.error("customer service problem detail: {}", pd);
        var message = Objects.nonNull(pd) ? pd.getDetail() : exception.getMessage();
        return ApplicationExceptions.invalidTradeRequest(message);
    }

}
