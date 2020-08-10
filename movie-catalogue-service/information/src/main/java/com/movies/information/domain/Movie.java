package com.movies.information.domain;

public class Movie {
    private String moiveId;
    private String name;

    public Movie() {}
    public Movie(String moiveId, String name) {
        this.moiveId = moiveId;
        this.name = name;
    }

    public String getMoiveId() {
        return moiveId;
    }

    public void setMoiveId(String moiveId) {
        this.moiveId = moiveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
