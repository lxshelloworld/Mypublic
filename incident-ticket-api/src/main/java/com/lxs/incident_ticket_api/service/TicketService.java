package com.lxs.incident_ticket_api.service;

import com.lxs.incident_ticket_api.entity.Ticket;
import com.lxs.incident_ticket_api.exception.TicketNotFoundException;
import com.lxs.incident_ticket_api.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        if (ticket.getTitle() == null || ticket.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        ticket.setStatus(Ticket.Status.OPEN);  // 默认状态
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllTickets(Ticket.Status status, Ticket.Priority priority, String systemName) {
        return ticketRepository.findByFilters(status, priority, systemName);
    }

    public Ticket getTicketById(Long id) {
       return ticketRepository.findById(id)
               .orElseThrow(() -> new TicketNotFoundException(id));
   }
/*    public Ticket getTicketById(Long id) {
        if (!ticketRepository.existsById(id) ) {
            throw new TicketNotFoundException(id);
        }
        return ticketRepository.findById(id);
    }*/

    public Ticket updateStatus(Long id, String statusStr) {
        Ticket ticket = getTicketById(id);
        try {
            Ticket.Status status = Ticket.Status.valueOf(statusStr.toUpperCase());
            ticket.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Must be one of: OPEN, INVESTIGATING, RESOLVED, CLOSED");
        }
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(id);
        }
        ticketRepository.deleteById(id);
    }

    public Ticket updateAssignee(Long id, String assignee) {
        Ticket ticket = getTicketById(id);
        ticket.setAssignee(assignee);
        return ticketRepository.save(ticket);
    }

    public Ticket updateNote(Long id, String note) {
        Ticket ticket = getTicketById(id);
        ticket.setNote(note);
        return ticketRepository.save(ticket);
    }
}
