package com.movies.catalog.api;

import com.movies.catalog.domain.CatalogItem;
import com.movies.catalog.domain.Movie;
import com.movies.catalog.domain.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("catalog")
@RestController
public class MovieCatalogController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public MovieCatalogController(DiscoveryClient discoveryClient, RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") final String userId) {

        UserRating ratings = restTemplate.getForObject(String.format("http://movie-ratings-server/rating/users/%s" ,userId), UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = this.restTemplate.getForObject(String.format("http://movie-info-server/movies/%s", rating.getMovieId()), Movie.class);
            return new CatalogItem(movie.getName(), "Robot trasforms!", rating.getRating());
        }).collect(Collectors.toList());
    }
}
//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri(String.format("http://localhost:8080/movies/%s", rating.getMovieId()))
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block(); //block Mono is fulfilled
