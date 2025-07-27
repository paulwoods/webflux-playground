package org.mrpaulwoods.playground.sec08.controller;

import org.mrpaulwoods.playground.sec08.dto.ProductDto;
import org.mrpaulwoods.playground.sec08.dto.UploadResponse;
import org.mrpaulwoods.playground.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "upload1", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts1(@RequestBody Flux<ProductDto> flux) {
        log.info("invoked 1");
        return productService.saveProducts(flux.doOnNext(dto -> log.info("received: {}", dto)))
                .then(productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    // million product upload
    @PostMapping(value = "upload2", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts2(@RequestBody Flux<ProductDto> flux) {
        log.info("invoked 2");
        return productService.saveProducts(flux)
                .then(productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    @GetMapping(value = "download1", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> downloadProducts1() {
        log.info("download products 1");
        return productService.allProducts1();
    }

}
