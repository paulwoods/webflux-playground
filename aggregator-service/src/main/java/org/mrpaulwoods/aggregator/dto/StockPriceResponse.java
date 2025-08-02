package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;

public record StockPriceResponse(
        Ticker ticker,
        Integer price
        ) {
}
