package com.devcodestack.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingStatus {
	
	@Builder.Default
	private boolean bookingState = false;
	private String bookingMsg;
}
