package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;
import org.mrpaulwoods.aggregator.tests.domain.TradeAction;

public record StockTradeResponse(
        Integer customerId,
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action,
        Integer totalPrice,
        Integer balance
) {
}
