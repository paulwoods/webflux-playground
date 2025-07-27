package org.mrpaulwoods.playground.sec09.service;

import org.mrpaulwoods.playground.sec09.dto.ProductDto;
import org.mrpaulwoods.playground.sec09.mapper.EntityDtoMapper;
import org.mrpaulwoods.playground.sec09.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> saveProduct(Mono<ProductDto> mono) {
        return mono
                .map(EntityDtoMapper::toEntity)
                .flatMap(repository::save)
                .map(EntityDtoMapper::toDto)
                .doOnNext(sink::tryEmitNext);
    }

    public Flux<ProductDto> productStream() {
        return sink.asFlux();
    }

}
