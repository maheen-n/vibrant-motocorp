package com.motorcorp.application.modules.ticket.dao;

import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.application.modules.ticket.dto.Ticket;
import com.motorcorp.application.modules.ticket.models.TicketFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

public interface TicketDao {
    void save(Ticket ticket);

    Ticket findById(Long id);

    Page<Ticket> findAll(TicketFilter filter);
}
