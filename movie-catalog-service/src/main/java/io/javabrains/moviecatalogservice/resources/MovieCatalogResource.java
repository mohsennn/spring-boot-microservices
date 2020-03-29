package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.UserRating;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod="getFallbackCatalog")
	public List<CatalogItem> getCtatlog(@PathVariable("userId") String userId) {

		UserRating ratings = restTemplate.getForObject(
				"http://ratings-data-service/ratingdata/users/" + userId,
				UserRating.class);

		return ratings
				.getUserRating()
				.stream()
				.map(rating -> {
					Movie movie = restTemplate.getForObject(
							"http://movie-info-service/movies/"
									+ rating.getMovieId(), Movie.class);

					return new CatalogItem(movie.getName(), "DESC_hardcoded",
							rating.getRating());
				}).collect(Collectors.toList());

	}

}
