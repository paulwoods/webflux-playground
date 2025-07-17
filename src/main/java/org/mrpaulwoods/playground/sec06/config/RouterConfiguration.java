package org.mrpaulwoods.playground.sec06.config;

import org.mrpaulwoods.playground.sec06.exceptions.CustomerNotFoundException;
import org.mrpaulwoods.playground.sec06.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Autowired
    private ApplicationExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions.route()
                .GET("customers", customerRequestHandler::allCustomers)
                .GET("customers/paginated", customerRequestHandler::getCustomersPaginated)
                .GET("customers/{id}", customerRequestHandler::getCustomer)
                .POST("customers", customerRequestHandler::saveCustomer)
                .PUT("customers/{id}", customerRequestHandler::updateCustomer)
                .DELETE("customers/{id}", customerRequestHandler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .build();
    }

}
