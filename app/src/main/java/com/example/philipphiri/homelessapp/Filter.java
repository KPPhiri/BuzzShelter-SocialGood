package com.example.philipphiri.homelessapp;

/**
 * Filter enum
 */
public enum Filter{
    INITIAL("No Filters"),
    GENDER("Gender"),
    AGE("Age");

    private final String filter;
    Filter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return filter;
    }

}
