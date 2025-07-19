package org.mrpaulwoods.playground.sec06.assignment_woods.config;

import org.mrpaulwoods.playground.sec06.assignment_woods.exceptions.BCannotBeZeroException;
import org.mrpaulwoods.playground.sec06.assignment_woods.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CalculatorRequestHandler {

    @Autowired
    private CalculatorService calculatorService;

    public Mono<ServerResponse> add(ServerRequest request) {
        int a = Integer.valueOf(request.pathVariable("a"), 10);
        int b = Integer.valueOf(request.pathVariable("b"), 10);
        int result = calculatorService.add(a, b);
        return ServerResponse.ok().bodyValue(result);
    }

    public Mono<ServerResponse> subtract(ServerRequest request) {
        int a = Integer.valueOf(request.pathVariable("a"), 10);
        int b = Integer.valueOf(request.pathVariable("b"), 10);
        int result = calculatorService.subtract(a, b);
        return ServerResponse.ok().bodyValue(result);
    }

    public Mono<ServerResponse> multiply(ServerRequest request) {
        int a = Integer.valueOf(request.pathVariable("a"), 10);
        int b = Integer.valueOf(request.pathVariable("b"), 10);
        int result = calculatorService.multiply(a, b);
        return ServerResponse.ok().bodyValue(result);
    }

    public Mono<ServerResponse> divide(ServerRequest request) {
        int a = Integer.valueOf(request.pathVariable("a"), 10);
        int b = Integer.valueOf(request.pathVariable("b"), 10);
        if (b == 0) {
            return Mono.error(BCannotBeZeroException::new);
        }
        int result = calculatorService.divide(a, b);
        return ServerResponse.ok().bodyValue(result);
    }

}

