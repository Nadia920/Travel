package com.java.Travel.model;

public enum Rating {
    ONE_STAR("ONE STAR"),
    TWO_STARS("TWO STARS"),
    THREE_STARS("THREE STARS"),
    FOUR_STARS("FOUR STARS"),
    FIVE_STARS("FIVE STARS");

    private final String name;

    Rating(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
