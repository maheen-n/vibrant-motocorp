package com.motorcorp.application.dashboard.controller;

import com.motorcorp.application.dashboard.models.DashboardFilter;
import com.motorcorp.application.dashboard.models.EnquiryInfo;
import com.motorcorp.application.dashboard.models.EnquiryStatus;
import com.motorcorp.application.dashboard.models.MaintenanceInfo;
import com.motorcorp.application.dashboard.models.MaintenanceStatus;
import com.motorcorp.application.dashboard.models.TicketInfo;
import com.motorcorp.application.dashboard.models.TicketStatus;
import com.motorcorp.application.dashboard.services.DashboardService;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @PostMapping(value = "maintenance/info")
    public ResponseModel<MaintenanceInfo> maintenanceInfo(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        MaintenanceInfo maintenanceInfo = dashboardService.getMaintenanceInfo(filter);
        return ResponseModel.of(maintenanceInfo);
    }

    @PostMapping(value = "maintenance/status")
    public ResponseModel<List<MaintenanceStatus>> maintenanceStatus(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        List<MaintenanceStatus> maintenanceStatuses = dashboardService.maintenanceStatus(filter);
        return ResponseModel.of(maintenanceStatuses);
    }

    @PostMapping(value = "ticket/info")
    public ResponseModel<TicketInfo> ticketInfo(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        TicketInfo ticketInfo = dashboardService.getTicketInfo(filter);
        return ResponseModel.of(ticketInfo);
    }

    @PostMapping(value = "ticket/status")
    public ResponseModel<List<TicketStatus>> ticketStatus(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        List<TicketStatus> ticketStatuses = dashboardService.getTicketStatus(filter);
        return ResponseModel.of(ticketStatuses);
    }

    @PostMapping(value = "enquiry/info")
    public ResponseModel<EnquiryInfo> enquiryInfo(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        EnquiryInfo enquiryInfo = dashboardService.getEnquiryInfo(filter);
        return ResponseModel.of(enquiryInfo);
    }

    @PostMapping(value = "enquiry/status")
    public ResponseModel<List<EnquiryStatus>> enquiryStatus(@RequestBody @Valid DashboardFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        List<EnquiryStatus> enquiryStatuses = dashboardService.getEnquiryStatus(filter);
        return ResponseModel.of(enquiryStatuses);
    }
}
