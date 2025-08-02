package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;

public record StockPriceResponse(
        Ticker ticker,
        Integer price
) {
}
