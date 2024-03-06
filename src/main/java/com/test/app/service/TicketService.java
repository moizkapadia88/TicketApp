package com.test.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.test.app.model.TicketReceipt;
import com.test.app.model.TicketRequest;

@Service
public class TicketService {

	private Map<String, TicketReceipt> tickets = new HashMap<>();
	private final double ticketPrice = 20.0;

	@Autowired
	private SeatAllocator seatAllocator;

	public TicketReceipt purchaseTicket(TicketRequest ticket) {
		String seat = allocateSeat();

		if (seat == null) {
			System.out.println("No tickets available");
			return null;
		}

		System.out.println("Ticket booked for user:"
				+ ticket.getUser().getEmail() + " - Seat:" + seat);
		TicketReceipt ticketReceipt = getTicketReceipt(ticket, seat);

		tickets.put(ticket.getUser().getEmail(), ticketReceipt);

		System.out.println(ticketReceipt);
		return ticketReceipt;
	}

	private TicketReceipt getTicketReceipt(TicketRequest ticket, String seat) {
		TicketReceipt ticketReceipt = new TicketReceipt();
		ticketReceipt.setFrom(ticket.getFrom());
		ticketReceipt.setTo(ticket.getTo());
		ticketReceipt.setUser(ticket.getUser());
		ticketReceipt.setPrice(ticketPrice);
		ticketReceipt.setSeat(seat);

		return ticketReceipt;
	}

	public TicketReceipt getReceipt(String userId) {
		return tickets.get(userId);
	}

	public Map<String, String> getUsersBySection(String section) {
		Map<String, String> usersBySection = new HashMap<>();
		for (Entry<String, TicketReceipt> entry : tickets.entrySet()) {
			if (entry.getValue().getSeat().startsWith(section)) {
				usersBySection.put(entry.getKey(), entry.getValue().getSeat());
			}
		}
		return usersBySection;
	}

	public boolean removeUser(String userId) {
		if (tickets.containsKey(userId)) {
			TicketReceipt ticketReceipt = tickets.remove(userId);
			seatAllocator.deallocateSeat(ticketReceipt.getSeat());
			System.out.println("User:" + userId + "  removed successfully ");
			return true;
		}

		System.out.println("User:" + userId + "  not found ");
		return false;
	}

	public boolean modifyUserSeat(String userId, String section, String seat) {
		TicketReceipt ticketReceipt = tickets.get(userId);
		if (ticketReceipt != null) {
			String currentSeat = ticketReceipt.getSeat();
			String requestedSet = section + seat;
			
			if (currentSeat != null) {
				if (currentSeat.equalsIgnoreCase(requestedSet)) {
					System.out.println("Requested seat:" + requestedSet
							+ "  successfully updated");
					return true;
				}

				String newSeat = seatAllocator.allocateSeat(section, seat);
				if (newSeat != null) {
					ticketReceipt.setSeat(newSeat);
					System.out.println("Requested seat:" + requestedSet
							+ "  successfully updated");
					return true;
				} else {
					System.err.println("Requested seat:" + requestedSet
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
