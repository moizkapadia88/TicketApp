package com.test.app.model;

public class Seat {
	private String seatNumber;
	private boolean available;

	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
		this.available = true;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Seat [seatNumber=" + seatNumber + ", available=" + available
				+ "]";
	}

}
