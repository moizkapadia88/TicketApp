package com.test.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.test.app.model.TicketReceipt;
import com.test.app.model.TicketRequest;
import com.test.app.model.User;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	@Mock
	private User user;

	@InjectMocks
	private TicketService ticketService;

	@Test
	public void testPurchaseTicket() {
		TicketRequest ticket = new TicketRequest();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");

		when(user.getEmail()).thenReturn("test@ticketapp.com");

		TicketReceipt purchasedTicket = ticketService.purchaseTicket(ticket);

		assertEquals("test@ticketapp.com", purchasedTicket.getUser().getEmail());
		assertEquals(20.0, purchasedTicket.getPrice());
	}

	@Test
	public void testGetReceipt() {
		TicketRequest ticket = new TicketRequest();
		ticket.setFrom("London");
		ticket.setTo("France");
		ticket.setUser(user);
		when(user.getEmail()).thenReturn("test@ticketapp.com");
		ticketService.purchaseTicket(ticket);

		TicketReceipt receipt = ticketService.getReceipt("test@ticketapp.com");

		assertEquals("test@ticketapp.com", receipt.getUser().getEmail());
		assertEquals(20.0, receipt.getPrice());
	}

	@Test
	public void testGetReceipt_NotFound() {
		TicketReceipt receipt = ticketService
				.getReceipt("nonexistent@ticketapp.com");
		assertNull(receipt);
	}

	@Test
	public void testGetUsersBySection() {
		TicketRequest ticket1 = new TicketRequest();
		ticket1.setUser(user);
		ticket1.setFrom("London");
		ticket1.setTo("France");
		when(user.getEmail()).thenReturn("user1@ticketapp.com");

		TicketRequest ticket2 = new TicketRequest();
		ticket2.setUser(user);
		ticket2.setFrom("London");
		ticket2.setTo("France");
		when(user.getEmail()).thenReturn("user2@ticketapp.com");

		ticketService.purchaseTicket(ticket1);
		ticketService.purchaseTicket(ticket2);

		Map<String, String> usersBySection = ticketService
				.getUsersBySection("A");
		assertEquals(2, usersBySection.size());
	}

	@Test
	public void testRemoveUser() {
		TicketRequest ticket = new TicketRequest();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");
		when(user.getEmail()).thenReturn("test@ticketapp.com");
		ticketService.purchaseTicket(ticket);

		boolean removed = ticketService.removeUser("test@ticketapp.com");
		assertEquals(true, removed);
		assertNull(ticketService.getReceipt("test@ticketapp.com"));
	}

	@Test
	public void testRemoveUser_NotFound() {
		boolean removed = ticketService.removeUser("nonexistent@ticketapp.com");
		assertEquals(false, removed);
	}

	@Test
	public void testModifyUserSeat() {
		TicketRequest ticket = new TicketRequest();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");
		when(user.getEmail()).thenReturn("test@ticketapp.com");
		ticketService.purchaseTicket(ticket);

		boolean modified = ticketService.modifyUserSeat("test@ticketapp.com",
				"A", "2");

		assertEquals(true, modified);
		assertEquals("A2", ticketService.getReceipt("test@ticketapp.com")
				.getSeat());
	}

	@Test
	public void testModifyUserSeat_NotFound() {
		boolean modified = ticketService.modifyUserSeat(
				"nonexistent@ticketapp.com", "A", "2");
		assertEquals(false, modified);
	}
}
