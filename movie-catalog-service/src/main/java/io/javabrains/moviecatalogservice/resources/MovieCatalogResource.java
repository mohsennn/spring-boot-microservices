package io.javabrains.moviecatalogservice.resources;



import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;

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

	/*first call to rating rest api*/
	UserRating ratings= restTemplate.getForObject("http://localhost:8083/ratingdata/users/"+userId, UserRating.class);

		
		return ratings.getUserRating().stream().map(rating-> {
			/*second call to movie rest api		 */
	 Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+ rating.getMovieId(), Movie.class);
				  
				  return new CatalogItem(movie.getName(), "DESC_hardcoded",rating.getRating());
				}).collect(Collectors.toList());

		 
	}

}
