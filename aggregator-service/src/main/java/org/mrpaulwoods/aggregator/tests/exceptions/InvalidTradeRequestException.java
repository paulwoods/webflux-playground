package org.mrpaulwoods.aggregator.tests.exceptions;

public class InvalidTradeRequestException extends RuntimeException {

    public InvalidTradeRequestException(String message) {
        super(message);
    }

}
