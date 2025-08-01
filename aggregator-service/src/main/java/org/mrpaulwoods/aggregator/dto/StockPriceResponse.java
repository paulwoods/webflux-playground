package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;

import java.time.LocalDateTime;

public record StockPriceResponse(
        Ticker ticker,
        Integer price,
) {
}
