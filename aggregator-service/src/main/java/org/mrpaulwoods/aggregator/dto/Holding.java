package org.mrpaulwoods.aggregator.dto;

import org.mrpaulwoods.aggregator.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
}
