package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;
import org.mrpaulwoods.aggregator.tests.domain.TradeAction;

public record TradeRequest(
        Ticker ticker,
        TradeAction action,
        Integer quantity
) {
}
