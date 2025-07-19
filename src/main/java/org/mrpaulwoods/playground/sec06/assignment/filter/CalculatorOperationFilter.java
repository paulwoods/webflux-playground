package org.mrpaulwoods.playground.sec06.assignment.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CalculatorOperationFilter implements WebFilter {

    private final Map<String, Operation> OPERATION_MAP = Arrays.stream(Operation.values())
            .collect(Collectors.toUnmodifiableMap(
                    Operation::getSymbol,
                    Function.identity(),
                    (o, o2) -> o));
    @Autowired
    private FilterErrorHandler errorHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String oName = exchange.getRequest().getHeaders().getFirst("operation");
        if (null == oName) {
            return errorHandler.sendProblemDetail(exchange, HttpStatus.BAD_REQUEST, "missing operation header");
        }

        Operation operation = OPERATION_MAP.getOrDefault(oName, null);
        if (null == operation) {
            return errorHandler.sendProblemDetail(exchange, HttpStatus.BAD_REQUEST, "missing operation header");
        }

        exchange.getAttributes().put("operation", operation);

        return chain.filter(exchange);
    }

}
