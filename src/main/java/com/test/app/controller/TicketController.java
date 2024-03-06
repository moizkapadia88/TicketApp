package com.test.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.model.Ticket;
import com.test.app.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping("/purchaseTicket")
	public ResponseEntity<Ticket> purchaseTicket(@RequestBody Ticket ticket) {
		Ticket purchasedTicket = ticketService.purchaseTicket(ticket);
		return ResponseEntity.ok(purchasedTicket);
	}

	@GetMapping("/receipts/{userId}")
	public ResponseEntity<Ticket> getReceipt(@PathVariable String userId) {
		Ticket ticket = ticketService.getReceipt(userId);
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ticket);
	}

	@GetMapping("/usersBySection")
	public ResponseEntity<Map<String, String>> getUsersBySection(
			@RequestParam String section) {
		Map<String, String> usersBySection = ticketService
				.getUsersBySection(section);
		return ResponseEntity.ok(usersBySection);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Void> removeUser(@PathVariable String userId) {
		boolean removed = ticketService.removeUser(userId);
		if (removed) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<Void> modifyUserSeat(@PathVariable String userId,
			@RequestBody Map<String, String> seatInfo) {
		String section = seatInfo.get("section");
		String seat = seatInfo.get("seat");
		boolean modified = ticketService.modifyUserSeat(userId, section, seat);
		if (modified) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
