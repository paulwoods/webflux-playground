package org.mrpaulwoods.playground.tests.sec08;

import org.mrpaulwoods.playground.sec08.dto.ProductDto;
import org.mrpaulwoods.playground.sec08.dto.UploadResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

    private final WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    public Mono<UploadResponse> uploadProducts1(Flux<ProductDto> flux) {
        return this.client.post()
                .uri("/products/upload1")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(flux, ProductDto.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);
    }

    public Mono<UploadResponse> uploadProducts2(Flux<ProductDto> flux) {
        return this.client.post()
                .uri("/products/upload2")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(flux, ProductDto.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);
    }

    public Flux<ProductDto> downloadProducts1() {
        return this.client.get()
                .uri("/products/download1")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

}
