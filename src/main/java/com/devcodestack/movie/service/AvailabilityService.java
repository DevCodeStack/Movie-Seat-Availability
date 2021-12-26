package com.devcodestack.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devcodestack.movie.entity.Movie;
import com.devcodestack.movie.model.Booking;
import com.devcodestack.movie.model.BookingStatus;
import com.devcodestack.movie.repository.AvailabilityRepo;
import static com.devcodestack.movie.util.MovieConstants.INVALID_MOVIE;
import static com.devcodestack.movie.util.MovieConstants.SEATS_BOOKED;
import static com.devcodestack.movie.util.MovieConstants.SEATS_NOTBOOKED;
import static com.devcodestack.movie.util.MovieConstants.SEATS_UNAVAILABLE;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AvailabilityService {
	
	private AvailabilityRepo availabilityRepo;
	
	public List<Movie> getMovieData(String movieName){
		return availabilityRepo.getMovieDataByName(movieName);
	}
	
	public BookingStatus updateMovieSeats(Booking booking) {
		BookingStatus status = BookingStatus.builder().build();
		log.info("Movie Name: {}", booking.getMovieName());
		List<Movie> movieList = getMovieData(booking.getMovieName());
		if(movieList.isEmpty()) {
			status.setBookingMsg(INVALID_MOVIE);
			return status;
		}
		
		for(Movie movie : movieList) {
			if(movie.getMovieName().equalsIgnoreCase(booking.getMovieName())) {
				if(movie.getAvailSeats() > booking.getNbrOfSeats()) {
					movie.setAvailSeats(movie.getAvailSeats() - booking.getNbrOfSeats());
					Integer count = availabilityRepo.updateMovieSeat(movie.getAvailSeats(), movie.getMovieId());
					if(count > 0) {
						status.setBookingMsg(SEATS_BOOKED);
						status.setBookingState(true);
						return status;
					} else {
						status.setBookingMsg(SEATS_NOTBOOKED);
						return status;
					}
				} else {
					status.setBookingMsg(SEATS_UNAVAILABLE);
					return status;
				}
			}
		}
		
		status.setBookingMsg(SEATS_NOTBOOKED);
		
		return status;
	}
}
