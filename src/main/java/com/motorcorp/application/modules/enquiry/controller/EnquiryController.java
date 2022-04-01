package com.motorcorp.application.modules.enquiry.controller;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.models.CustomerCreateRequest;
import com.motorcorp.application.modules.customer.models.CustomerUpdateRequest;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.customer.services.CustomerService;
import com.motorcorp.application.modules.enquiry.models.EnquiryCreateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryFilter;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdate;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryView;
import com.motorcorp.application.modules.enquiry.services.EnquiryService;
import com.motorcorp.application.modules.ticket.models.TicketUpdate;
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
@RequestMapping(value = "api/enquiry",  produces = MediaType.APPLICATION_JSON_VALUE)
public class EnquiryController {
    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<EnquiryView> create(@AuthenticationPrincipal UserSession session,
                                             @RequestBody @Valid EnquiryCreateRequest enquiryCreateRequest,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        enquiryCreateRequest.setInitiatedId(session.getUserId());
        EnquiryView enquiryView = enquiryService.create(enquiryCreateRequest);
        return ResponseModel.of(enquiryView);
    }

    @GetMapping("{id}")
    public ResponseModel<EnquiryView> get(@PathVariable("id") Long id) {
        EnquiryView enquiryView = enquiryService.get(id);
        return ResponseModel.of(enquiryView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<EnquiryView>> getAllEnquiries(@Valid @RequestBody EnquiryFilter params) {
        PaginatedList<EnquiryView> enquiryView = enquiryService.get(params);
        return ResponseModel.of(enquiryView);
    }

    @PutMapping("{id}")
    public ResponseModel<EnquiryView> update(@PathVariable("id") Long id,
                                             @Valid @RequestBody EnquiryUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        EnquiryView enquiryView = enquiryService.update(id, updateRequest);
        return ResponseModel.of(enquiryView);
    }

    @PutMapping("{id}/activity")
    public ResponseModel<String> activity(@AuthenticationPrincipal UserSession session,
                                          @PathVariable("id") Long id,
                                          @Valid @RequestBody EnquiryUpdate update,
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
                        throw new AuthenticationException("You Does have permission to close enquiry");
                    enquiryService.updateStatus(id, update);
                    break;
                case "NOTE":
                    if (StringUtils.isBlank(update.getMessage()))
                        throw new ControllerException("Note Cannot be blank");
                    enquiryService.updateActivity(id, update);
                    break;
                default:
                    throw new ControllerException("invalid field 'kind' value ->" + update.getKind());
            }
        } catch (IllegalArgumentException e) {
            log.error("Error updating Enquiry", e);
            throw new ControllerException("Invalid request");
        } catch (Exception e) {
            log.error("Error updating Enquiry", e);
            throw new ControllerException(e.getLocalizedMessage());
        }
        return ResponseModel.of("Updated Successfully");
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserSession session) {
        EnquiryView enquiryView = enquiryService.delete(id, session.getUserId());
        return ResponseModel.of(enquiryView.getId());
    }

    @GetMapping("activity/{id}")
    public ResponseModel<List<ActivityView>> getAllActivityPaginated(@PathVariable("id") Long enquiryId,
                                                                 @RequestParam int page,
                                                                 @Valid @RequestParam(defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(limit, 100), Sort.by(Sort.Direction.DESC, "id"));
        List<ActivityView> activities = activityService.getAllPaginated(Type.Category.ENQUIRY, enquiryId, pageRequest);
        return ResponseModel.of(activities);
    }
}
