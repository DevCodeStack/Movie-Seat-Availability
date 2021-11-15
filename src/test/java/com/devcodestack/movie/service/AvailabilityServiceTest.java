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
public class AvailabilityServiceTest {
	
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
	public void getMovieData_success() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		List<Movie> movieResp = availabilityService.getMovieData(booking.getMovieName());
		assertEquals(movieResp.get(0).getAvailSeats(), 100);
	}
	
	/**
	 * updateMovieSeats positive test
	 */
	@Test
	public void updateMovieSeats_success() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		when(availabilityRepo.updateMovieSeat(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(1);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(status.getBookingMsg(), MovieConstants.SEATS_BOOKED);
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	public void updateMovieSeats_notbooked() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(100).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		when(availabilityRepo.updateMovieSeat(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(0);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(status.getBookingMsg(), MovieConstants.SEATS_NOTBOOKED);
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	public void updateMovieSeats_unavailable() {
		Movie movie = Movie.builder().movieName("movieTest").availSeats(9).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(status.getBookingMsg(), MovieConstants.SEATS_UNAVAILABLE);
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	public void updateMovieSeats_empty() {
		List<Movie> movieList = new ArrayList<>();
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(status.getBookingMsg(), MovieConstants.INVALID_MOVIE);
	}
	
	/**
	 * updateMovieSeats negative test
	 */
	@Test
	public void updateMovieSeats_incorrect_movie() {
		Movie movie = Movie.builder().movieName("movieReTest").availSeats(9).seatPrice(100).build();
		List<Movie> movieList = new ArrayList<>();
		movieList.add(movie);
		
		when(availabilityRepo.getMovieDataByName(ArgumentMatchers.any())).thenReturn(movieList);
		
		BookingStatus status = availabilityService.updateMovieSeats(booking);
		assertEquals(status.getBookingMsg(), MovieConstants.SEATS_NOTBOOKED);
	}
}
