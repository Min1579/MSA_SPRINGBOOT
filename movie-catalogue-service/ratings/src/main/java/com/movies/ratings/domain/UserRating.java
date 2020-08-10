package com.movies.ratings.domain;

import java.io.Serializable;
import java.util.List;

public class UserRating implements Serializable {
    private List<Rating> userRating;

    public UserRating() {}
    public UserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
