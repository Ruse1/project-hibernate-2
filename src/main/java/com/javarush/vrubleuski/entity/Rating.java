package com.javarush.vrubleuski.entity;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private String name;

    Rating(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
