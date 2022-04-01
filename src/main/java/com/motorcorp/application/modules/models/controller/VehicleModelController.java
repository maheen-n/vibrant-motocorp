package com.motorcorp.application.modules.models.controller;

import com.motorcorp.application.modules.models.models.VehicleModelCreateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelFilter;
import com.motorcorp.application.modules.models.models.VehicleModelUpdateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelView;
import com.motorcorp.application.modules.models.services.VehicleModelService;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import com.motorcorp.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/vehicle_models", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleModelController {
    @Autowired
    private VehicleModelService vehicleModelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<VehicleModelView> create(@RequestBody @Valid VehicleModelCreateRequest userCreateRequest,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        VehicleModelView userView = vehicleModelService.create(userCreateRequest);
        return ResponseModel.of(userView);
    }

    @GetMapping("{id}")
    public ResponseModel<VehicleModelView> get(@PathVariable("id") Long id) {
        VehicleModelView userView = vehicleModelService.get(id);
        return ResponseModel.of(userView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<VehicleModelView>> getAllModels(@Valid @RequestBody VehicleModelFilter params) {
        PaginatedList<VehicleModelView> userView = vehicleModelService.get(params);
        return ResponseModel.of(userView);
    }

    @PutMapping("{id}")
    public ResponseModel<VehicleModelView> update(@PathVariable("id") Long id, @Valid @RequestBody VehicleModelUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        VehicleModelView userView = vehicleModelService.update(id, updateRequest);
        return ResponseModel.of(userView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id) {
        VehicleModelView userView = vehicleModelService.delete(id);
        return ResponseModel.of(userView.getId());
    }

}
