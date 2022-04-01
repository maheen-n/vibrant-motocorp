package com.motorcorp.application.dashboard.services.impl;

import com.motorcorp.application.dashboard.models.DashboardFilter;
import com.motorcorp.application.dashboard.models.EnquiryInfo;
import com.motorcorp.application.dashboard.models.EnquiryStatus;
import com.motorcorp.application.dashboard.models.MaintenanceInfo;
import com.motorcorp.application.dashboard.models.MaintenanceStatus;
import com.motorcorp.application.dashboard.models.TicketInfo;
import com.motorcorp.application.dashboard.models.TicketStatus;
import com.motorcorp.application.dashboard.services.DashboardService;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.motorcorp.application.constants.DbConstants.ENQUIRY;
import static com.motorcorp.application.constants.DbConstants.MAINTENANCE;
import static com.motorcorp.application.constants.DbConstants.TICKET;
import static org.jooq.impl.DSL.*;

@Slf4j
@Repository
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DSLContext context;

    @Override
    public MaintenanceInfo getMaintenanceInfo(DashboardFilter filter) {

        MaintenanceInfo maintenanceInfo = context.select(
                count().as("maintenances"),
                sum(iif(DSL.noCondition().and(field("service_type").eq("FREE")), 1, 0)).as("free_maintenances"),
                sum(iif(DSL.noCondition().and(field("service_type").eq("PAID")), 1, 0)).as("paid_maintenances")
        )
                .from(MAINTENANCE)
                .where(filter.getConditions())
                .fetchOneInto(MaintenanceInfo.class);
        return maintenanceInfo;
    }

    @Override
    public List<MaintenanceStatus> maintenanceStatus(DashboardFilter filter) {
        List<MaintenanceStatus> maintenanceStatuses = context.select(
                field("maintenance_status").as("status"),
                count().as("count"))
                .from(MAINTENANCE)
                .where(filter.getConditions())
                .groupBy(field("maintenance_status"))
                .fetch().into(MaintenanceStatus.class);
        return maintenanceStatuses;
    }

    @Override
    public TicketInfo getTicketInfo(DashboardFilter filter) {
        TicketInfo ticketInfo = context.select(
                count().as("tickets"),
                sum(iif(DSL.noCondition().and(field("ticket").eq("VEHICLE")), 1, 0)).as("vehicles"),
                sum(iif(DSL.noCondition().and(field("ticket").eq("RIDING_GEAR")), 1, 0)).as("riding_gears"),
                sum(iif(DSL.noCondition().and(field("ticket").eq("ACCESSORIES")), 1, 0)).as("accessories"),
                sum(iif(DSL.noCondition().and(field("ticket").eq("SPARE_PARTS")), 1, 0)).as("spare_parts")
        )
                .from(TICKET)
                .where(filter.getConditions())
                .fetchOneInto(TicketInfo.class);
        return ticketInfo;
    }

    @Override
    public List<TicketStatus> getTicketStatus(DashboardFilter filter) {
        List<TicketStatus> ticketStatuses = context.select(
                field("ticket_status").as("status"),
                count().as("count"))
                .from(TICKET)
                .where(filter.getConditions())
                .groupBy(field("ticket_status"))
                .fetch().into(TicketStatus.class);
        return ticketStatuses;
    }

    @Override
    public EnquiryInfo getEnquiryInfo(DashboardFilter filter) {
        EnquiryInfo enquiryInfo = context.select(
                count().as("enquiries"),
                sum(iif(DSL.noCondition().and(field("enquiry").eq("VEHICLE")), 1, 0)).as("vehicles"),
                sum(iif(DSL.noCondition().and(field("enquiry").eq("RIDING_GEAR")), 1, 0)).as("riding_gears"),
                sum(iif(DSL.noCondition().and(field("enquiry").eq("ACCESSORIES")), 1, 0)).as("accessories"),
                sum(iif(DSL.noCondition().and(field("enquiry").eq("SPARE_PARTS")), 1, 0)).as("spare_parts")
        )
                .from(ENQUIRY)
                .where(filter.getConditions())
                .fetchOneInto(EnquiryInfo.class);
        return enquiryInfo;
    }

    @Override
    public List<EnquiryStatus> getEnquiryStatus(DashboardFilter filter) {
        List<EnquiryStatus> enquiryStatuses = context.select(
                field("enquiry_status").as("status"),
                count().as("count"))
                .from(ENQUIRY)
                .where(filter.getConditions())
                .groupBy(field("enquiry_status"))
                .fetch().into(EnquiryStatus.class);
        return enquiryStatuses;
    }
}
