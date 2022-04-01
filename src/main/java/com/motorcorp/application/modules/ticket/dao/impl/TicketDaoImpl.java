package com.motorcorp.application.modules.ticket.dao.impl;

import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.application.modules.ticket.dao.TicketDao;
import com.motorcorp.application.modules.ticket.dao.TicketSpecifications;
import com.motorcorp.application.modules.ticket.dto.Ticket;
import com.motorcorp.application.modules.ticket.models.TicketFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TicketDaoImpl extends TicketSpecifications<Ticket> implements TicketDao {
    @Autowired
    private TicketRepository ticketRepo;

    @Override
    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Ticket> findAll(TicketFilter filter) {
        Specification<Ticket> specification = Specification.where(
                query(filter.getQuery())
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
                        .and(ticketType(filter.getTicket()))
                        .and(ticketStatus(filter.getTicketStatus()))
        );
        return ticketRepo.findAll(specification, filter.filterPageable());
    }
}
