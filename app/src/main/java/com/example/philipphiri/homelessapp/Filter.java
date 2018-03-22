package com.example.philipphiri.homelessapp;

/**
 * Created by Bianca on 3/4/18.
 */

public enum Filter{
    INITIAL("No Filters"),
    GENDER("Gender"),
    AGE("Age");

    private String filter;
    Filter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return filter;
    }

}
