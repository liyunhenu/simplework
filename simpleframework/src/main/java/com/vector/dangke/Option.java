package com.vector.dangke;

public enum Option {
    FIRST("A"),
    SECOND("B"),
    THIRD("C"),
    FOURTH("D");
    private String alias;

    private Option(String alias){
        this.alias=alias;
    }
}
