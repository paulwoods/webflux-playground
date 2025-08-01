package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;
import org.mrpaulwoods.aggregator.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {
}
