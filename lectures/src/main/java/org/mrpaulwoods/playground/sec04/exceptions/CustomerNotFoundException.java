package org.mrpaulwoods.playground.sec04.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Customer [id=%d] was not found";

    public CustomerNotFoundException(Integer id) {
        super(MESSAGE.formatted(id));
    }

}
