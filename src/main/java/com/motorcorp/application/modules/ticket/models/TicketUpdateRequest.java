package com.motorcorp.application.modules.ticket.models;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.ticket.dto.Ticket;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class TicketUpdateRequest extends AbstractRequestUpdateEntity<Ticket> {
    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    private String email;

    private String comment;

    @NotNull
    private Type.Ticket ticket;

    private String ticketFor;

    private Long customerId;

    private Long initiatedId;

    @Override
    public Ticket update(Ticket entity) {
        if (name != null) entity.setName(name);
        if (phone != null) entity.setPhone(phone);
        if (email != null) entity.setEmail(email);
        if (comment != null) entity.setComment(comment);
        if (ticket != null) entity.setTicket(ticket);
        if (ticketFor != null) entity.setTicketFor(ticketFor);
        return entity;
    }
}
