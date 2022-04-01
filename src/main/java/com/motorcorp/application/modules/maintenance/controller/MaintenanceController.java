package com.motorcorp.application.modules.maintenance.controller;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.models.CustomerCreateRequest;
import com.motorcorp.application.modules.customer.models.CustomerUpdateRequest;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.customer.services.CustomerService;
import com.motorcorp.application.modules.maintenance.models.CustomerInfoView;
import com.motorcorp.application.modules.maintenance.models.CustomerRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceCreateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceFilter;
import com.motorcorp.application.modules.maintenance.models.MaintenanceUpdateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceView;
import com.motorcorp.application.modules.maintenance.models.VehicleInfoView;
import com.motorcorp.application.modules.maintenance.models.VehicleRequest;
import com.motorcorp.application.modules.maintenance.service.MaintenanceService;
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
@RequestMapping(value = "api/maintenance",  produces = MediaType.APPLICATION_JSON_VALUE)
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<MaintenanceView> create(@AuthenticationPrincipal UserSession session,
                                                 @RequestBody @Valid MaintenanceCreateRequest customerCreateRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        customerCreateRequest.setInitiatedId(session.getUserId());
        MaintenanceView maintenanceView = maintenanceService.create(customerCreateRequest);
        return ResponseModel.of(maintenanceView);
    }

    @GetMapping("{id}")
    public ResponseModel<MaintenanceView> get(@PathVariable("id") Long id) {
        MaintenanceView maintenanceView = maintenanceService.get(id);
        return ResponseModel.of(maintenanceView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<MaintenanceView>> getAllMaintenance(@Valid @RequestBody MaintenanceFilter params) {
        PaginatedList<MaintenanceView> maintenanceView = maintenanceService.get(params);
        return ResponseModel.of(maintenanceView);
    }

    @PutMapping("{id}")
    public ResponseModel<MaintenanceView> update(@AuthenticationPrincipal UserSession userSession,
                                                 @PathVariable("id") Long id,
                                                 @Valid @RequestBody MaintenanceUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        updateRequest.setInitiatedId(userSession.getUserId());
        MaintenanceView maintenanceView = maintenanceService.update(id, updateRequest);
        return ResponseModel.of(maintenanceView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserSession userSession) {
        MaintenanceView maintenanceView = maintenanceService.delete(id, userSession.getUserId());
        return ResponseModel.of(maintenanceView.getId());
    }

    @PutMapping("{maintenanceId}/customer")
    public ResponseModel<CustomerInfoView> updateCustomer(@PathVariable("maintenanceId") Long maintenanceId,
                                                          @RequestBody @Valid CustomerRequest customerRequest,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        CustomerInfoView customerInfoView = maintenanceService.updateMaintenanceCustomer(maintenanceId, customerRequest);
        return ResponseModel.of(customerInfoView);
    }

    @PutMapping("{maintenanceId}/vehicle")
    public ResponseModel<VehicleInfoView> updateVehicle(@PathVariable("maintenanceId") Long maintenanceId,
                                                        @RequestBody @Valid VehicleRequest vehicleRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        VehicleInfoView vehicleInfoView = maintenanceService.updateMaintenanceVehicle(maintenanceId, vehicleRequest);
        return ResponseModel.of(vehicleInfoView);
    }

    @GetMapping("activity/{id}")
    public ResponseModel<List<ActivityView>> getAllActivityPaginated(@PathVariable("id") Long maintenanceId,
                                                                 @RequestParam int page,
                                                                 @Valid @RequestParam(defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(limit, 100), Sort.by(Sort.Direction.DESC, "id"));
        List<ActivityView> activities = activityService.getAllPaginated(Type.Category.MAINTENANCE, maintenanceId, pageRequest);
        return ResponseModel.of(activities);
    }
}
