package com.devcodestack.movie.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
	
	@Id
	@Column(name = "movieid")
	private Integer movieId;
	
	@Column(name = "moviename")
	private String movieName;
	
	@Column(name = "availseats")
	private Integer availSeats;
	
	@Column(name = "seatprice")
	private Integer seatPrice;
	
	@Column(name = "isactive")
	private char isActive;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "createdon")
	private Date createdOn;
	
	@Column(name = "updatedby")
	private String updatedBy;
	
	@Column(name = "updatedon")
	private Date updatedOn;
}
