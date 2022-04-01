package com.motorcorp.application.modules.ticket.controller;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.ticket.models.TicketFilter;
import com.motorcorp.application.modules.ticket.models.TicketUpdate;
import com.motorcorp.application.modules.ticket.models.TicketCreateRequest;
import com.motorcorp.application.modules.ticket.models.TicketUpdateRequest;
import com.motorcorp.application.modules.ticket.models.TicketView;
import com.motorcorp.application.modules.ticket.services.TicketService;
import com.motorcorp.exceptions.AuthenticationException;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import com.motorcorp.models.ResponseModel;
import com.motorcorp.models.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<TicketView> create(@AuthenticationPrincipal UserSession session,
                                            @RequestBody @Valid TicketCreateRequest ticketCreateRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        ticketCreateRequest.setInitiatedId(session.getUserId());
        TicketView ticketView = ticketService.create(ticketCreateRequest);
        return ResponseModel.of(ticketView);
    }

    @GetMapping("{id}")
    public ResponseModel<TicketView> get(@PathVariable("id") Long id) {
        TicketView ticketView = ticketService.get(id);
        return ResponseModel.of(ticketView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<TicketView>> getAllTickets(@Valid @RequestBody TicketFilter params) {
        PaginatedList<TicketView> ticketView = ticketService.get(params);
        return ResponseModel.of(ticketView);
    }

    @PutMapping("{id}")
    public ResponseModel<TicketView> update(@PathVariable("id") Long id,
                                            @Valid @RequestBody TicketUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        TicketView ticketView = ticketService.update(id, updateRequest);
        return ResponseModel.of(ticketView);
    }

    @PutMapping("{id}/activity")
    public ResponseModel<String> activity(@AuthenticationPrincipal UserSession session,
                                          @PathVariable("id") Long id,
                                          @Valid @RequestBody TicketUpdate update,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        update.setBy(session.getUserId());
        try {
            switch (update.getKind()) {
                case "STATUS":
                    if (Status.Ticket.CLOSED.toString().equals(update.getTo()) && session.getUserRole() != Role.ADMIN)
                        throw new AuthenticationException("You Does have permission to close ticket");
                    ticketService.updateStatus(id, update);
                    break;
                case "NOTE":
                    if (StringUtils.isBlank(update.getMessage()))
                        throw new ControllerException("Note Cannot be blank");
                    ticketService.updateActivity(id, update);
                    break;
                default:
                    throw new ControllerException("invalid field 'kind' value ->" + update.getKind());
            }
        } catch (IllegalArgumentException e) {
            log.error("Error updating Ticket", e);
            throw new ControllerException("Invalid request");
        } catch (Exception e) {
            log.error("Error updating Ticket", e);
            throw new ControllerException(e.getLocalizedMessage());
        }
        return ResponseModel.of("Updated Successfully");
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserSession session) {
        TicketView ticketView = ticketService.delete(id, session.getUserId());
        return ResponseModel.of(ticketView.getId());
    }

    @GetMapping("activity/{id}")
    public ResponseModel<List<ActivityView>> getAllActivityPaginated(@PathVariable("id") Long ticketId,
                                                                     @RequestParam int page,
                                                                     @Valid @RequestParam(defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(limit, 100), Sort.by(Sort.Direction.DESC, "id"));
        List<ActivityView> activities = activityService.getAllPaginated(Type.Category.TICKET, ticketId, pageRequest);
        return ResponseModel.of(activities);
    }
}
