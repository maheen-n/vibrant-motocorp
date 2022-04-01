package com.motorcorp.application.modules.maintenance.models;

import com.motorcorp.application.enums.Status;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.models.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceFilter extends BaseFilter {
    private Status.Maintenance[] maintenanceStatus;

    private Long vehicleId;

    public Status.Maintenance[] getMaintenanceStatus() {
        return maintenanceStatus == null || maintenanceStatus.length == 0 ? new Status.Maintenance[]{Status.Maintenance.NEW, Status.Maintenance.IN_PROGRESS, Status.Maintenance.COMPLETED} : maintenanceStatus;
    }

    public void setMaintenanceStatus(Status.Maintenance maintenanceStatus) {
        this.maintenanceStatus = new Status.Maintenance[]{maintenanceStatus};
    }
}
