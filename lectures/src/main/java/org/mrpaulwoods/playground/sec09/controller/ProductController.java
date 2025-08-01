package org.mrpaulwoods.playground.sec09.controller;

import org.mrpaulwoods.playground.sec09.dto.ProductDto;
import org.mrpaulwoods.playground.sec09.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> mono) {
        return productService.saveProduct(mono);
    }

    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> productStream() {
        return productService.productStream();
    }

    // browse to http://localhost:8080/products/stream
    // browse to http://localhost:8080/products/stream/50
    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> productStream2(@PathVariable Integer maxPrice) {
        return productService
                .productStream()
                .filter(dto -> dto.getPrice() <= maxPrice);
    }

}
