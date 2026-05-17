package com.lxs.incident_ticket_api.repository;

import com.lxs.incident_ticket_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(Ticket.Status status);
    List<Ticket> findByPriority(Ticket.Priority priority);
    List<Ticket> findBySystemName(String systemName);

    @Query("SELECT t FROM Ticket t WHERE (:status IS NULL OR t.status = :status) AND (:priority IS NULL OR t.priority = :priority) AND (:systemName IS NULL OR t.systemName = :systemName)")
    List<Ticket> findByFilters(@Param("status") Ticket.Status status, @Param("priority") Ticket.Priority priority, @Param("systemName") String systemName);
}
