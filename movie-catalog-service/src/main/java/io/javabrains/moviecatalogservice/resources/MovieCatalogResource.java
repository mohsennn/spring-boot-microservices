package io.javabrains.moviecatalogservice.resources;



import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCtatlog(@PathVariable("userId") String userId) {

		// RestTemplate restTemplate = new RestTemplate();
		List<Rating> ratings = Arrays.asList(

		new Rating("1234", 4), new Rating("5678", 5));

		return ratings
				.stream()
				.map(rating -> {
				  Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+ rating.getMovieId(), Movie.class);
					//utilisation de web client 
					/*Movie movie = webClientBuilder.build()
							.get()
							.uri("http://localhost:8082/movies/"+ rating.getMovieId())
							.retrieve()
							.bodyToMono(Movie.class)
							.block();
							*/

					/*
					 * movie.getName():retourne par la 1er web service
					 * rating.getRating():retourne pa l'appel au 2 eme
					 * webservice RatingData Service
					 */
					return new CatalogItem(movie.getName(), "DESC_hardcoded",
							rating.getRating());
				}).collect(Collectors.toList());

		// return Collections.singletonList(new
		// CatalogItem("Transformers","Tets", 4));
	}

}
