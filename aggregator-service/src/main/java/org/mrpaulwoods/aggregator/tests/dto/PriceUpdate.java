package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;

import java.time.LocalDateTime;

public record PriceUpdate(
        Ticker ticker,
        Integer price,
        LocalDateTime time
) {
}
