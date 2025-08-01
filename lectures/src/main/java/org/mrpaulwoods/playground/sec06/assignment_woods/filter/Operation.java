package org.mrpaulwoods.playground.sec06.assignment_woods.filter;

public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLE("*"),
    DIVIDE("/");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
