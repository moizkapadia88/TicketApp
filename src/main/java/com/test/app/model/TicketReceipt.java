package com.test.app.model;

public class TicketReceipt {
	String from;
	String to;
	User user;
	double price;
	String seat;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "TicketReceipt [from=" + from + ", to=" + to + ", user=" + user
				+ ", price=" + price + ", seat=" + seat + "]";
	}

}
