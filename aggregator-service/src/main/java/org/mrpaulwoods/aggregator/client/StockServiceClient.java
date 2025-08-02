package org.mrpaulwoods.aggregator.client;

import org.mrpaulwoods.aggregator.dto.PriceUpdate;
import org.mrpaulwoods.aggregator.dto.StockPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StockServiceClient {

    private static final Logger log = LoggerFactory.getLogger(StockServiceClient.class);

    public final WebClient client;

    public StockServiceClient(WebClient client) {
        this.client = client;
    }

    public Mono<StockPriceResponse> getStockPrice(String ticker) {
        return this.client.get()
                .uri("/stock/{ticker}", ticker)
                .retrieve()
                .bodyToMono(StockPriceResponse.class);
    }

    public Flux<PriceUpdate> getPriceUpdate() {
        return this.client.get()
                .uri("/stock/price-stream")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(PriceUpdate.class);
    }

}
