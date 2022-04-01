package com.motorcorp.application.modules.customer.controller;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.models.CustomerCreateRequest;
import com.motorcorp.application.modules.customer.models.CustomerFilter;
import com.motorcorp.application.modules.customer.models.CustomerUpdateRequest;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.customer.services.CustomerService;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.application.modules.user.models.UserCreateRequest;
import com.motorcorp.application.modules.user.models.UserUpdateRequest;
import com.motorcorp.application.modules.user.models.UserView;
import com.motorcorp.application.modules.user.services.UserService;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import com.motorcorp.models.ResponseModel;
import com.motorcorp.models.UserSession;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<CustomerView> create(@AuthenticationPrincipal UserSession session,
                                              @RequestBody @Valid CustomerCreateRequest customerCreateRequest,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        customerCreateRequest.setInitiatedId(session.getUserId());
        CustomerView customerView = customerService.create(customerCreateRequest);
        return ResponseModel.of(customerView);
    }

    @GetMapping("{id}")
    public ResponseModel<CustomerView> get(@PathVariable("id") Long id) {
        CustomerView customerView = customerService.get(id);
        return ResponseModel.of(customerView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<CustomerView>> getAllCustomers(@Valid @RequestBody CustomerFilter params) {
        PaginatedList<CustomerView> customerView = customerService.get(params);
        return ResponseModel.of(customerView);
    }

    @PutMapping("{id}")
    public ResponseModel<CustomerView> update(@PathVariable("id") Long id, @Valid @RequestBody CustomerUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        CustomerView customerView = customerService.update(id, updateRequest);
        return ResponseModel.of(customerView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserSession session) {
        CustomerView customerView = customerService.delete(id, session.getUserId());
        return ResponseModel.of(customerView.getId());
    }

    @GetMapping("activity/{id}")
    public ResponseModel<List<ActivityView>> getAllActivityPaginated(@PathVariable("id") Long customerId,
                                                                     @RequestParam int page,
                                                                     @Valid @RequestParam(defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(limit, 100), Sort.by(Sort.Direction.DESC, "id"));
        List<ActivityView> activities = activityService.getAllPaginated(Type.Category.CUSTOMER, customerId, pageRequest);
        return ResponseModel.of(activities);
    }
}
