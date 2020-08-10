package com.movies.ratings.api;

import com.movies.ratings.domain.Rating;
import com.movies.ratings.domain.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("rating")
@RestController
public class MovieRatingController {

    @GetMapping("{movieId}")
    public Rating getRating(@PathVariable("movieId") final String movieId) {
        return new Rating(movieId, 55);
    }

    @GetMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") final String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1234",4),
                new Rating("5678", 5)
        );
        return new UserRating(ratings);
    }
}
