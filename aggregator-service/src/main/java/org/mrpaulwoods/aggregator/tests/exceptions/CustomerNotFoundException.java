package org.mrpaulwoods.aggregator.tests.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Customer [id=%d] not found.";

    public CustomerNotFoundException(Integer id) {
        super(MESSAGE.formatted(id));
    }
}
