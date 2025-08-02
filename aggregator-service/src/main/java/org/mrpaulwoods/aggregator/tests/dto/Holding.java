package org.mrpaulwoods.aggregator.tests.dto;

import org.mrpaulwoods.aggregator.tests.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
}
