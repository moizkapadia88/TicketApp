package com.test.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.test.app.model.Seat;

@Service
public class SeatAllocator {
	private Map<String, List<Seat>> availableSeatsBySection;
	private final int max_seats = 10;
	String[] sections = { "A", "B" };

	public SeatAllocator() {
		availableSeatsBySection = new HashMap<>();

		for (String section : sections) {
			List<Seat> seats = new ArrayList<>();

			for (int i = 1; i <= max_seats; i++) {
				Seat seat = new Seat(section + i);
				seats.add(seat);
			}

			availableSeatsBySection.put(section, seats);
		}
	}

	public String allocateSeat() {
		for (String section : sections) {
			List<Seat> availableSeats = availableSeatsBySection.get(section);
			for (Seat seat : availableSeats) {
				if (seat.isAvailable()) {
					seat.setAvailable(false);
					return seat.getSeatNumber();
				}
			}
		}
		return null;
	}

	public void deallocateSeat(String seat) {
		String section = seat.substring(0, 1);
		int seatNumber = getIntSeatNumber(seat.substring(1));

		if (seatNumber != -1) {
			List<Seat> availableSeats = availableSeatsBySection.get(section);
			availableSeats.get(seatNumber - 1).setAvailable(true);
		}
	}

	public String allocateSeat(String section, String seatNumber) {
		List<Seat> availableSeats = availableSeatsBySection.get(section);
		int seatNum = getIntSeatNumber(seatNumber);

		if (seatNum != -1) {
			Seat seat = availableSeats.get(seatNum - 1);
			if (seat.isAvailable()) {
				seat.setAvailable(false);
				return seat.getSeatNumber();
			}
		}

		return null;
	}

	private int getIntSeatNumber(String seatNumber) {
		int intSeatNumber = Integer.valueOf(seatNumber);
		if (intSeatNumber >= 1 && intSeatNumber <= max_seats) {
			return intSeatNumber;
		} else {
			return -1;
		}
	}

}
