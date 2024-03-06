package com.test.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import com.test.app.model.Ticket;

@Service
public class TicketService {

	/*public static void main(String[] args) {
		TrainService service = new TrainService();

		User user = new User();
		user.setEmail("mk1@gmail.com");
		user.setFirstName("Moiz");
		user.setLastName("Kapadia");

		User user2 = new User();
		user2.setEmail("mk2@gmail.com");
		user2.setFirstName("Moiz");
		user2.setLastName("Kapadia");
		//
		// User user3 = new User();
		// user3.setEmail("mk3@gmail.com");
		// user3.setFirstName("Moiz");
		// user3.setLastName("Kapadia");

		Ticket ticket = new Ticket();
		ticket.setFrom("London");
		ticket.setTo("France");
		ticket.setUser(user);

		service.purchaseTicket(ticket);

		// ticket.setUser(user3);
		// service.purchaseTicket(ticket);

		service.modifyUserSeat("mk1@gmail.com", "B", "1");
		// service.modifyUserSeat("mk2@gmail.com", "B", "2");
		// service.modifyUserSeat("mk3@gmail.com", "A", "1");

		ticket.setUser(user2);
		service.purchaseTicket(ticket);
		
		service.modifyUserSeat("mk2@gmail.com", "B", "1");
		
		service.removeUser("mk1@gmail.com");
		// service.removeUser("mk3@gmail.com");

		// service.modifyUserSeat("mk1@gmail.com", "A", "1");
		service.modifyUserSeat("mk2@gmail.com", "B", "1");
		// service.modifyUserSeat("mk3@gmail.com", "A", "1");
		
	}*/

	private Map<String, Ticket> tickets = new HashMap<>(); // userId -> Ticket
	private Map<String, String> userSeats = new HashMap<>(); // userId -> seat
	private final double price = 20.0;

	@Autowired
	private SeatAllocator seatAllocator;

//	public TrainService() {
//		seatAllocator = new SeatAllocator();
//	}

	public Ticket purchaseTicket(Ticket ticket) {
		ticket.setPrice(price);
		String seat = allocateSeat();

		if (seat == null) {
			// ticket cannot be booked.
			System.out.println("Sorry!! No tickets available");
			return null;
		}

		System.out.println("Ticket booked for user:"
				+ ticket.getUser().getEmail() + " - Seat:" + seat);
		userSeats.put(ticket.getUser().getEmail(), seat);
		tickets.put(ticket.getUser().getEmail(), ticket);

		return ticket;
	}

	public Ticket getReceipt(String userId) {
		return tickets.get(userId);
	}

	public Map<String, String> getUsersBySection(String section) {
		Map<String, String> usersBySection = new HashMap<>();
		for (Map.Entry<String, String> entry : userSeats.entrySet()) {
			if (entry.getValue().startsWith(section)) {
				usersBySection.put(entry.getKey(), entry.getValue());
			}
		}
		return usersBySection;
	}

	public boolean removeUser(String userId) {
		if (tickets.containsKey(userId)) {
			String seat = userSeats.remove(userId);
			tickets.remove(userId);
			seatAllocator.deallocateSeat(seat);
			return true;
		}
		return false;
	}

	public boolean modifyUserSeat(String userId, String section, String seat) {
		if (tickets.containsKey(userId)) {
			String currentSeat = userSeats.get(userId);
			if (currentSeat != null) {
				String newSeat = seatAllocator.allocateSeat(section, seat);
				if (newSeat != null) {
					userSeats.put(userId, newSeat);
					System.out.println("Requested seat:" + section + seat
							+ "  successfully updated");
					return true;
				} else {
					System.err.println("Requested seat:" + section + seat
							+ " is not available.");
				}
			}
		} else {
			System.err.println("No valid ticket found for user id: " + userId);
		}

		return false;
	}

	private String allocateSeat() {
		return seatAllocator.allocateSeat();
	}
}
