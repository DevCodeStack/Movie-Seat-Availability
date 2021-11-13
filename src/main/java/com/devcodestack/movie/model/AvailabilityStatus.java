package com.devcodestack.movie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityStatus {
	
	private String movieName;
	@Builder.Default
	private boolean seatAvailable = false;
	private Integer nbrOfSeats;
}
