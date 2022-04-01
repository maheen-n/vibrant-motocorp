package com.motorcorp.application.modules.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.ticket.dto.Ticket;
import com.motorcorp.dto.AbstractRequestEntity;
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
public class TicketCreateRequest extends AbstractRequestEntity<Ticket> {
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

    @JsonIgnore
    private Long initiatedId;
}
