package org.mrpaulwoods.playground.sec06.assignment_woods.exceptions;

public class BCannotBeZeroException extends RuntimeException {
    public BCannotBeZeroException() {
        super("b cannot be zero");
    }
}
