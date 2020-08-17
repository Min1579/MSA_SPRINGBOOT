package com.movies.catalog.domain;

public class Movie {
    private final String moiveId;
    private final String name;

    public Movie(String moiveId, String name) {
        this.moiveId = moiveId;
        this.name = name;
    }

    public String getMoiveId() {
        return moiveId;
    }
    public String getName() {
        return name;
    }
}
