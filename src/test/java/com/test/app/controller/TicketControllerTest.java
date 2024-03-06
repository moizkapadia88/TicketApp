package com.test.app.controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.app.model.Ticket;
import com.test.app.service.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPurchaseTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setFrom("London");
        ticket.setTo("France");

        when(ticketService.purchaseTicket(any())).thenReturn(ticket);

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk());
    }
}
