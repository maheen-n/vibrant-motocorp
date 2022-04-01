package com.motorcorp.application.modules.ticket.models;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.models.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketFilter extends BaseFilter {
    private Type.Ticket[] ticket;

    private Long customerId;

    private Status.Ticket[] ticketStatus;

    public void setTicket(Type.Ticket ticket) {
        this.ticket = new Type.Ticket[]{ticket};
    }

    public void setTicketStatus(Status.Ticket ticketStatus) {
        this.ticketStatus = new Status.Ticket[]{ticketStatus};
    }

    public Status.Ticket[] getTicketStatus() {
        return ticketStatus == null || ticketStatus.length == 0 ? new Status.Ticket[]{Status.Ticket.NEW, Status.Ticket.IN_PROGRESS, Status.Ticket.COMPLETED} : ticketStatus;
    }
}
