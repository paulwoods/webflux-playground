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

    // demo 1
    // single bean creating all the routes
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

        // predicate version
        // if predicate returns true, then the function will handle the route
        // .GET(req -> true, this.customerRequestHandler::allCustomers)

        // pattern & predicate
//        .GET("customers", req -> req.headers().firstHeader("key").equals("value"), customerRequestHandler::allCustomers)

        // match the path, and path should end in 1 + a digit
//                .GET("customers/{id}", RequestPredicates.path("*/1?"), customerRequestHandler::getCustomer)

    }

    // demo 2

    // multiple routing beans are supported
    // each bean needs it's own error handling
//    @Bean
//    public RouterFunction<ServerResponse> customerRoutes1() {
//        return RouterFunctions.route()
//                .POST("customers", customerRequestHandler::saveCustomer)
//                .PUT("customers/{id}", customerRequestHandler::updateCustomer)
//                .DELETE("customers/{id}", customerRequestHandler::deleteCustomer)
//                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
//                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> customerRoutes2() {
//        return RouterFunctions.route()
//                .GET("customers", customerRequestHandler::allCustomers)
//                .GET("customers/paginated", customerRequestHandler::getCustomersPaginated)
//                .GET("customers/{id}", customerRequestHandler::getCustomer)
//                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
//                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
//                .build();
//    }


    // demo 3
    // one bean can call methods
    // shared error handling
    // filter examples
//    @Bean
//    public RouterFunction<ServerResponse> customerRoutes1() {
//        return RouterFunctions.route()
//                .path("customers", this::customerRoutes2)
//
//                .POST("customers", customerRequestHandler::saveCustomer)
//                .PUT("customers/{id}", customerRequestHandler::updateCustomer)
//                .DELETE("customers/{id}", customerRequestHandler::deleteCustomer)
//                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
//                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
//
//                .filter((request, chain) -> {
//                    // do the filter stuff here
//
//                    // success - run the next in the chain
//                    return chain.handle(request);
//
//                    // or, return error
//                    // return ServerResponse.badRequest().build();
//                })
//                .filter((request, chain) -> {
//                    // another filter
//                    return chain.handle(request);
//                })
//
//                .build();
//    }
//
//    RouterFunction<ServerResponse> customerRoutes2() {
//        return RouterFunctions.route()
//                .GET("paginated", customerRequestHandler::getCustomersPaginated)
//                .GET("{id}", customerRequestHandler::getCustomer)
//                .GET(customerRequestHandler::allCustomers)
//                .build();
//    }

}
