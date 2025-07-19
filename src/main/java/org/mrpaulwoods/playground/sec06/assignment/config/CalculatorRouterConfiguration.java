package org.mrpaulwoods.playground.sec06.assignment.config;

import org.mrpaulwoods.playground.sec06.assignment.exceptions.BCannotBeZeroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorRouterConfiguration {

    @Autowired
    private CalculatorRequestHandler calculatorRequestHandler;

    @Autowired
    private CalculatorExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> calculatorRoutes() {

        return RouterFunctions.route()
                .onError(BCannotBeZeroException.class, this.exceptionHandler::handleException)
                .GET("calculator/{a}/{b}",
                        RequestPredicates.headers(headers -> "+".equals(headers.firstHeader("operation"))),
                        calculatorRequestHandler::add
                )
                .GET("calculator/{a}/{b}",
                        RequestPredicates.headers(headers -> "-".equals(headers.firstHeader("operation"))),
                        calculatorRequestHandler::subtract
                )
                .GET("calculator/{a}/{b}",
                        RequestPredicates.headers(headers -> "*".equals(headers.firstHeader("operation"))),
                        calculatorRequestHandler::multiply
                )
                .GET("calculator/{a}/{b}",
                        RequestPredicates.headers(headers -> "/".equals(headers.firstHeader("operation"))),
                        calculatorRequestHandler::divide
                )
                .build();

    }

}
