package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;
import org.mrpaulwoods.aggregator.domain.TradeAction;

public record TradeRequest(
        Ticker ticker,
        TradeAction action,
        Integer quantity
) {
}
