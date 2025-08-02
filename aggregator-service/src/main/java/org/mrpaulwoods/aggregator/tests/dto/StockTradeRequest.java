package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;
import org.mrpaulwoods.aggregator.tests.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {
}
