package org.mrpaulwoods.aggregator.controller;

import org.mrpaulwoods.aggregator.dto.CustomerInformation;
import org.mrpaulwoods.aggregator.dto.StockTradeResponse;
import org.mrpaulwoods.aggregator.dto.TradeRequest;
import org.mrpaulwoods.aggregator.service.CustomerPortfolioService;
import org.mrpaulwoods.aggregator.validator.RequestValidator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerPortfolioController {

    private final CustomerPortfolioService customerPortfolioService;

    public CustomerPortfolioController(CustomerPortfolioService customerPortfolioService) {
        this.customerPortfolioService = customerPortfolioService;
    }

    @GetMapping("{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
        return customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("{customerId}/trade")
    public Mono<StockTradeResponse> trade(
            @PathVariable Integer customerId,
            @RequestBody Mono<TradeRequest> mono
    ) {
        return mono.transform(RequestValidator.validate())
                .flatMap(req -> this.customerPortfolioService.trade(customerId, req));
    }

}
