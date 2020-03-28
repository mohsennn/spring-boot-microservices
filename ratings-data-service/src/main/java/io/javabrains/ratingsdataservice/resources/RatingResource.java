package io.javabrains.ratingsdataservice.resources;

import io.javabrains.ratingsdataservice.models.Rating;
import io.javabrains.ratingsdataservice.models.UserRating;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating("movieId", 4);
	}

	@RequestMapping("users/{userId}")
	public UserRating getRatings(@PathVariable("userId") String userId) {

		List<Rating> ratings = Arrays.asList(
								new Rating("1234", 4), 
				 				new Rating("4567", 3));

		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;

	}

}
