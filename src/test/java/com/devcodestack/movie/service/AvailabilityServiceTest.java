package com.devcodestack.movie.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcodestack.movie.entity.Movie;
import com.devcodestack.movie.model.Booking;
import com.devcodestack.movie.model.BookingStatus;
import com.devcodestack.movie.repository.AvailabilityRepo;
import com.devcodestack.movie.util.MovieConstants;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {
	
	@InjectMocks
	private AvailabilityService availabilityService;
	
	@Mock
	private AvailabilityRepo availabilityRepo;
	
	Booking booking;
	
	@BeforeEach
	public void setup() {
		booking = new Booking("movieTest", 10);
	}
	
	/**
	 * getMovieData positive test
	 */
	@Test
	void getMovieData_success() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		List<Movie> movieResp = availabilityService.getMovieData(booking.getMovieName());
		assertEquals(100, movieResp.get(0).getAvailSeats());
	}
	
	/**
	 * updateMovieSeats positive test
	 */
	@Test
	void updateMovieSeats_success() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		when(availabilityRepo.updateMovieSeat(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(1);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(MovieConstants.SEATS_BOOKED, status.getBookingMsg());
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	void updateMovieSeats_notbooked() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		when(availabilityRepo.updateMovieSeat(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(0);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(MovieConstants.SEATS_NOTBOOKED, status.getBookingMsg());
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	void updateMovieSeats_unavailable() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(9).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(MovieConstants.SEATS_UNAVAILABLE, status.getBookingMsg());
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	void updateMovieSeats_empty() {
		List<Movie> movieList = new ArrayList<>();
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(MovieConstants.INVALID_MOVIE, status.getBookingMsg());
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	void updateMovieSeats_incorrect_movie() {
		Movie movie = Movie.builder().movieName("movieReTest").availSeats(9).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(MovieConstants.SEATS_NOTBOOKED, status.getBookingMsg());
	}
}
