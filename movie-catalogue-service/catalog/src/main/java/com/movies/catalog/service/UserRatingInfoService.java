package com.movies.catalog.service;

import com.movies.catalog.domain.Rating;
import com.movies.catalog.domain.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserRatingInfoService {
    private final RestTemplate restTemplate;

    public UserRatingInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        }
    )
    public UserRating getUserRating(@PathVariable("userId") final String userId) {
        return restTemplate.getForObject(String.format("http://movie-ratings-server/rating/users/%s" ,userId), UserRating.class);
    }

    private UserRating getFallbackUserRating(@PathVariable("userId") final String userId) {
        return new UserRating(Collections.singletonList(new Rating(userId, 0)));
    }
}
