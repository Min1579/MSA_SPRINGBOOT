package com.movies.information.api;

import com.movies.information.domain.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("movies")
@RestController
public class MovieInformationController {

    @GetMapping("{movieId}")
    public Movie getMovieInformation(@PathVariable("movieId") final String movieId) {
        return new Movie(movieId, "Test");
    }
}
