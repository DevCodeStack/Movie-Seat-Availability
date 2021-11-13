package com.devcodestack.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devcodestack.movie.entity.Movie;

@Repository
@Transactional
public interface AvailabilityRepo extends JpaRepository<Movie, Integer> {
	
	@Query(value = "select * from Movie m where m.moviename = ?1", nativeQuery = true)
	public List<Movie> getMovieDataByName(String movieName);
	
	@Modifying
	@Query(value = "update Movie m set availseats = ?1 where movieid = ?2", nativeQuery = true)
	public Integer updateMovieSeat(Integer nbrOfSeats, Integer movieId);
}
