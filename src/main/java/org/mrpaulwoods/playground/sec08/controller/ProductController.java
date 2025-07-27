package org.mrpaulwoods.playground.sec08.controller;

import org.mrpaulwoods.playground.sec08.dto.ProductDto;
import org.mrpaulwoods.playground.sec08.dto.UploadResponse;
import org.mrpaulwoods.playground.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux) {
        log.info("invoked");
        return productService.saveProducts(flux.doOnNext(dto -> log.info("received: {}", dto)))
                .then(productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

}
