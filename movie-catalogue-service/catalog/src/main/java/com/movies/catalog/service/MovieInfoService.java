package com.movies.catalog.service;

import com.movies.catalog.domain.CatalogItem;
import com.movies.catalog.domain.Movie;
import com.movies.catalog.domain.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MovieInfoService {
    private final RestTemplate restTemplate;

    @Autowired
    public MovieInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public CatalogItem getCatalogItem(final Rating rating) {
        Movie movie = this.restTemplate.getForObject(String.format("http://movie-info-server/movies/%s", rating.getMovieId()), Movie.class);
        return new CatalogItem(movie.getName(), "Robot trasforms!", rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(final Rating rating) {
        return new CatalogItem("Movie Name Not Found","", rating.getRating());
    }
}
