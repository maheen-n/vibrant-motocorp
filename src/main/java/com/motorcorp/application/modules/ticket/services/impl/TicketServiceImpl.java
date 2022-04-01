package com.motorcorp.application.modules.ticket.services.impl;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.ticket.dao.TicketDao;
import com.motorcorp.application.modules.ticket.dto.Ticket;
import com.motorcorp.application.modules.ticket.models.TicketCreateRequest;
import com.motorcorp.application.modules.ticket.models.TicketFilter;
import com.motorcorp.application.modules.ticket.models.TicketUpdate;
import com.motorcorp.application.modules.ticket.models.TicketUpdateRequest;
import com.motorcorp.application.modules.ticket.models.TicketView;
import com.motorcorp.application.modules.ticket.services.TicketService;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public TicketView create(TicketCreateRequest request) {
        Ticket ticket = request.getEntity();
        if (request.getCustomerId() != null) {
            Customer customer = customerDao.findById(request.getCustomerId());
            if (customer == null) {
                log.error("Customer not found for id {}", request.getCustomerId());
                throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
            }
            ticket.setCustomer(customer);
        }
        ticket.setStatus(EntityStatus.ACTIVE);
        ticket.setTicketStatus(Status.Ticket.NEW);
        ticketDao.save(ticket);
        activityService.insert(Activity.builder()
                .category(Type.Category.TICKET)
                .activity(Type.Activity.CREATED)
                .activityFor(ticket.getId())
                .userId(request.getInitiatedId())
                .build());
        if (ticket.getCustomer() != null) {
            activityService.insert(Activity.builder()
                    .category(Type.Category.CUSTOMER)
                    .activity(Type.Activity.TICKET_RAISED)
                    .activityFor(ticket.getCustomer().getId())
                    .activityId(ticket.getId())
                    .build());
        }
        return ticket.getView(TicketView.class);
    }

    @Override
    public TicketView get(Long id) {
        Ticket ticket = ticketDao.findById(id);
        if (ticket == null) {
            log.error("Ticket not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.TICKET_NOT_FOUND);
        }
        return ticket.getView(TicketView.class);
    }

    @Override
    public PaginatedList<TicketView> get(TicketFilter filter) {
        Page<Ticket> page = ticketDao.findAll(filter);
        return new PaginatedList<>(page, TicketView.class);
    }

    @Override
    @Transactional
    public TicketView update(Long id, TicketUpdateRequest updateRequest) {
        Ticket ticket = ticketDao.findById(id);
        if (ticket == null) {
            log.error("Ticket not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.TICKET_NOT_FOUND);
        }
        int hash = ticket.hashCode();
        updateRequest.update(ticket);
        if (hash == ticket.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        ticketDao.save(ticket);
        return ticket.getView(TicketView.class);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, TicketUpdate update) {
        Status.Ticket status = Status.Ticket.valueOf(update.getTo());
        Ticket ticket = ticketDao.findById(id);
        if (ticket == null) {
            log.error("Ticket not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.TICKET_NOT_FOUND);
        }
        Status.Ticket oldStatus = ticket.getTicketStatus();
        if (oldStatus == status) {
            log.info("No change in status found. exiting...");
            return;
        }
        ticket.setTicketStatus(status);
        ticketDao.save(ticket);
        activityService.insert(Activity.builder()
                .category(Type.Category.TICKET)
                .activity(Type.Activity.STATUS)
                .activityFor(ticket.getId())
                .from(oldStatus.toString())
                .to(status.toString())
                .userId(update.getBy())
                .build());
    }

    @Override
    @Transactional
    public void updateActivity(Long id, TicketUpdate update) {
        Ticket ticket = ticketDao.findById(id);
        if (ticket == null) {
            log.error("Ticket not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.TICKET_NOT_FOUND);
        }
        activityService.insert(Activity.builder()
                .category(Type.Category.TICKET)
                .activity(Type.Activity.NOTE)
                .message(update.getMessage())
                .activityFor(id)
                .userId(update.getBy())
                .build());
    }

    @Override
    @Transactional
    public TicketView delete(Long id, Long userId) {
        Ticket ticket = ticketDao.findById(id);
        if (ticket == null) {
            log.error("Ticket not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.TICKET_NOT_FOUND);
        }
        ticket.setStatus(EntityStatus.DELETED);
        ticketDao.save(ticket);
        activityService.insert(Activity.builder()
                .category(Type.Category.TICKET)
                .activity(Type.Activity.DELETED)
                .activityFor(ticket.getId())
                .userId(userId)
                .build());
        return ticket.getView(TicketView.class);
    }
}
