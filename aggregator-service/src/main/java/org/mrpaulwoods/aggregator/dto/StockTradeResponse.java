package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;
import org.mrpaulwoods.aggregator.domain.TradeAction;

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
