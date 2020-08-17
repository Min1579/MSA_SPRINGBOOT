package com.movies.catalog.api;

import com.movies.catalog.domain.CatalogItem;
import com.movies.catalog.domain.UserRating;
import com.movies.catalog.service.MovieInfoService;
import com.movies.catalog.service.UserRatingInfoService;
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

    private final MovieInfoService movieService;
    private final UserRatingInfoService userRatingService;

    @Autowired
    public MovieCatalogController(DiscoveryClient discoveryClient,
                                  RestTemplate restTemplate,
                                  WebClient.Builder webClientBuilder,
                                  MovieInfoService movieService,
                                  UserRatingInfoService userRatingService) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
        this.movieService = movieService;
        this.userRatingService = userRatingService;
    }

    @GetMapping("{userId}")
    //@HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") final String userId) {
        UserRating ratings = userRatingService.getUserRating(userId);

        return ratings.getUserRating().stream()
                .map(movieService::getCatalogItem)
                .collect(Collectors.toList());
    }

//    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") final String userId) {
//        return Collections.singletonList(new CatalogItem("No movie", "", 0));
//    }

}
//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri(String.format("http://localhost:8080/movies/%s", rating.getMovieId()))
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block(); //block Mono is fulfilled
