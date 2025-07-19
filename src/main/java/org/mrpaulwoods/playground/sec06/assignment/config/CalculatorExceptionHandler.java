package org.mrpaulwoods.playground.sec06.assignment.config;

import org.mrpaulwoods.playground.sec06.assignment.exceptions.BCannotBeZeroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Service
public class CalculatorExceptionHandler {

    public Mono<ServerResponse> handleException(BCannotBeZeroException ex, ServerRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex, request, problem -> {
            problem.setType(URI.create("http://www.example.com/problems/invalid-input"));
            problem.setTitle("Invalid Input");
        });
    }

    Mono<ServerResponse> handleException(HttpStatus status, Exception ex, ServerRequest request, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setInstance(URI.create(request.path()));
        consumer.accept(problem);
        return ServerResponse.status(status).bodyValue(problem);
    }

}
