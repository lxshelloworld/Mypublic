package com.lxs.incident_ticket_api.controller;

import com.lxs.incident_ticket_api.entity.Ticket;
import com.lxs.incident_ticket_api.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket created = ticketService.createTicket(ticket);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(
            @RequestParam(value = "status", required = false) Ticket.Status status,
            @RequestParam(value = "priority", required = false) Ticket.Priority priority,
            @RequestParam(value = "systemName", required = false) String systemName) {
        List<Ticket> tickets = ticketService.getAllTickets(status, priority, systemName);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> updateStatus(@PathVariable Long id, @RequestBody String status) {
        Ticket updated = ticketService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<Ticket> updateAssignee(@PathVariable Long id, @RequestBody String assignee) {
        Ticket updated = ticketService.updateAssignee(id, assignee);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/note")
    public ResponseEntity<Ticket> updateNote(@PathVariable Long id, @RequestBody String note) {
        Ticket updated = ticketService.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
