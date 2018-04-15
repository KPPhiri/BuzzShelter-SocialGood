package com.example.philipphiri.homelessapp;


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
