package com.motorcorp.application.modules.vehicles.controller;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.user.models.UserCreateRequest;
import com.motorcorp.application.modules.user.models.UserUpdateRequest;
import com.motorcorp.application.modules.user.models.UserView;
import com.motorcorp.application.modules.user.services.UserService;
import com.motorcorp.application.modules.vehicles.models.VehicleCreateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleFilter;
import com.motorcorp.application.modules.vehicles.models.VehicleUpdateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
import com.motorcorp.application.modules.vehicles.services.VehicleService;
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
@RequestMapping(value = "api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<VehicleView> create(@AuthenticationPrincipal UserSession session,
                                             @RequestBody @Valid VehicleCreateRequest vehicleCreateRequest,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        vehicleCreateRequest.setInitiatedId(session.getUserId());
        VehicleView vehicleView = vehicleService.create(vehicleCreateRequest);
        return ResponseModel.of(vehicleView);
    }

    @GetMapping("{id}")
    public ResponseModel<VehicleView> get(@PathVariable("id") Long id) {
        VehicleView vehicleView = vehicleService.get(id);
        return ResponseModel.of(vehicleView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<VehicleView>> getAllVehicles(@Valid @RequestBody VehicleFilter params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        PaginatedList<VehicleView> vehicleView = vehicleService.get(params);
        return ResponseModel.of(vehicleView);
    }

    @PutMapping("{id}")
    public ResponseModel<VehicleView> update(@PathVariable("id") Long id, @Valid @RequestBody VehicleUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        VehicleView vehicleView = vehicleService.update(id, updateRequest);
        return ResponseModel.of(vehicleView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserSession session) {
        VehicleView vehicleView = vehicleService.delete(id, session.getUserId());
        return ResponseModel.of(vehicleView.getId());
    }

    @GetMapping("activity/{id}")
    public ResponseModel<List<ActivityView>> getAllActivityPaginated(@PathVariable("id") Long vehicleId,
                                                                 @RequestParam int page,
                                                                 @Valid @RequestParam(defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(limit, 100), Sort.by(Sort.Direction.DESC, "id"));
        List<ActivityView> activities = activityService.getAllPaginated(Type.Category.VEHICLE, vehicleId, pageRequest);
        return ResponseModel.of(activities);
    }
}
