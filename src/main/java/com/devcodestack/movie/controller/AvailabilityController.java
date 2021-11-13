package com.devcodestack.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcodestack.movie.entity.Movie;
import com.devcodestack.movie.model.AvailabilityStatus;
import com.devcodestack.movie.model.Booking;
import com.devcodestack.movie.model.BookingStatus;
import com.devcodestack.movie.service.AvailabilityService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/avail")
@AllArgsConstructor
@Slf4j
public class AvailabilityController {
	
	private AvailabilityService availabilityService;
	
	@GetMapping("/check/{movieName}")
	public ResponseEntity<AvailabilityStatus> checkSeatAvailability(@PathVariable("movieName") String movieName){
		log.debug("checkSeatAvailability invoked...");
		log.info("Status check initiated for movie {}", movieName);
		AvailabilityStatus status = AvailabilityStatus.builder().build();
		boolean movieCheck = false;
		Movie requestedMovie = null;
		if(null == movieName) {
			return ResponseEntity.ok(status);
		}
		
		List<Movie> movieList = availabilityService.getMovieData(movieName);
		
		for(Movie movie : movieList) {
			if(movie.getMovieName().equalsIgnoreCase(movieName)) {
				movieCheck = true;
				requestedMovie = movie;
				break;
			}
		}
		
		if(movieCheck) {
			return ResponseEntity.ok(AvailabilityStatus.builder()
					.movieName(movieName)
					.seatAvailable(movieCheck)
					.nbrOfSeats(requestedMovie.getAvailSeats()).build());
		}
		return ResponseEntity.ok(status);
	}
	
	@PostMapping("/update")
	public ResponseEntity<BookingStatus> updateMovieSeats(@RequestBody Booking booking){
		return ResponseEntity.ok(availabilityService.updateMovieSeats(booking));
	}
}
