package com.motorcorp.application.dashboard.services;

import com.motorcorp.application.dashboard.models.DashboardFilter;
import com.motorcorp.application.dashboard.models.EnquiryInfo;
import com.motorcorp.application.dashboard.models.EnquiryStatus;
import com.motorcorp.application.dashboard.models.MaintenanceInfo;
import com.motorcorp.application.dashboard.models.MaintenanceStatus;
import com.motorcorp.application.dashboard.models.TicketInfo;
import com.motorcorp.application.dashboard.models.TicketStatus;

import java.util.List;

public interface DashboardService {
    MaintenanceInfo getMaintenanceInfo(DashboardFilter filter);

    List<MaintenanceStatus> maintenanceStatus(DashboardFilter filter);

    TicketInfo getTicketInfo(DashboardFilter filter);

    List<TicketStatus> getTicketStatus(DashboardFilter filter);

    EnquiryInfo getEnquiryInfo(DashboardFilter filter);

    List<EnquiryStatus> getEnquiryStatus(DashboardFilter filter);
}
