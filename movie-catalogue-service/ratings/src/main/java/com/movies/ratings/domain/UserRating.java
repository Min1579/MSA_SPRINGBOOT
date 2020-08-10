package com.movies.ratings.domain;

import java.util.List;

public class UserRating {
    private List<Rating> userRating;

    public UserRating() {}
    public UserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
