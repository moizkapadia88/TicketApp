package com.test.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;

import com.test.app.model.Ticket;
import com.test.app.model.User;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	@Mock
	private User user;

	@InjectMocks
	private TicketService ticketService;

	@Test
	public void testPurchaseTicket() {
		Ticket ticket = new Ticket();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");

		when(user.getEmail()).thenReturn("test@example.com");

		Ticket purchasedTicket = ticketService.purchaseTicket(ticket);

		assertEquals("test@example.com", purchasedTicket.getUser().getEmail());
		assertEquals(20.0, purchasedTicket.getPrice());
	}

	@Test
	public void testGetReceipt() {
		Ticket ticket = new Ticket();
		ticket.setFrom("London");
		ticket.setTo("France");
		ticket.setUser(user);
		when(user.getEmail()).thenReturn("test@example.com");
		ticketService.purchaseTicket(ticket);

		Ticket receipt = ticketService.getReceipt("test@example.com");

		assertEquals("test@example.com", receipt.getUser().getEmail());
		assertEquals(20.0, receipt.getPrice());
	}

	@Test
	public void testGetReceipt_NotFound() {
		Ticket receipt = ticketService.getReceipt("nonexistent@example.com");
		assertNull(receipt);
	}

	@Test
	public void testGetUsersBySection() {
		Ticket ticket1 = new Ticket();
		ticket1.setUser(user);
		ticket1.setFrom("London");
		ticket1.setTo("France");
		when(user.getEmail()).thenReturn("user1@example.com");

		Ticket ticket2 = new Ticket();
		ticket2.setUser(user);
		ticket2.setFrom("London");
		ticket2.setTo("France");
		when(user.getEmail()).thenReturn("user2@example.com");

		ticketService.purchaseTicket(ticket1);
		ticketService.purchaseTicket(ticket2);

		Map<String, String> usersBySection = ticketService
				.getUsersBySection("A");
		assertEquals(2, usersBySection.size());
	}

	@Test
	public void testRemoveUser() {
		Ticket ticket = new Ticket();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");
		when(user.getEmail()).thenReturn("test@example.com");
		ticketService.purchaseTicket(ticket);

		boolean removed = ticketService.removeUser("test@example.com");
		assertEquals(true, removed);
		assertNull(ticketService.getReceipt("test@example.com"));
	}

	@Test
	public void testRemoveUser_NotFound() {
		boolean removed = ticketService.removeUser("nonexistent@example.com");
		assertEquals(false, removed);
	}

/*	@Test
	public void testModifyUserSeat() {
		Ticket ticket = new Ticket();
		ticket.setUser(user);
		ticket.setFrom("London");
		ticket.setTo("France");
		when(user.getEmail()).thenReturn("test@example.com");
		ticketService.purchaseTicket(ticket);

		boolean modified = ticketService.modifyUserSeat("test@example.com", "A",
				"2");

		assertEquals(true, modified);
		assertEquals("A2", ticketService.getReceipt("test@example.com")
				.getUser().getSeat());
	}

	@Test
	public void testModifyUserSeat_NotFound() {
		boolean modified = ticketService.modifyUserSeat(
				"nonexistent@example.com", "A", "2");
		assertFalse(modified);
	}*/
}
