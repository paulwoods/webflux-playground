package org.mrpaulwoods.aggregator.tests.service;

import org.mrpaulwoods.aggregator.tests.client.CustomerServiceClient;
import org.mrpaulwoods.aggregator.tests.client.StockServiceClient;
import org.mrpaulwoods.aggregator.tests.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerPortfolioService {

    private final StockServiceClient stockServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public CustomerPortfolioService(
            StockServiceClient stockServiceClient,
            CustomerServiceClient customerServiceClient
    ) {
        this.stockServiceClient = stockServiceClient;
        this.customerServiceClient = customerServiceClient;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return this.customerServiceClient.getCustomerInformation(customerId);
    }

    public Mono<StockTradeResponse> trade(Integer customerId, TradeRequest request) {
        return this.stockServiceClient.getStockPrice(request.ticker())
                .map(StockPriceResponse::price)
                .map(price -> this.toStockTradeRequest(request, price))
                .flatMap(req -> customerServiceClient.trade(customerId, req));
    }

    public StockTradeRequest toStockTradeRequest(TradeRequest request, Integer price) {
        return new StockTradeRequest(
                request.ticker(),
                price,
                request.quantity(),
                request.action()
        );
    }


}
