package com.motorcorp.application.modules.maintenance.dao;

import com.motorcorp.application.enums.Status;
import com.motorcorp.dto.AbstractRepositorySpecification;
import com.motorcorp.dto.AbstractTransactionalEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class MaintenanceSpecifications<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.join("customerInfo").get("name"), contains(expression)),
                builder.like(root.join("customerInfo").get("phone"), contains(expression)),
                builder.like(root.join("vehicleInfo").get("model"), contains(expression)),
                builder.like(root.join("vehicleInfo").get("registrationNumber"), contains(expression))
        );
    }

    public Specification<T> vehicleId(Long vehicleId) {
        if (vehicleId == null) return null;
        return (root, query, builder) -> {
            return builder.equal(root.join("vehicleInfo").get("vehicleId"), vehicleId);
        };
    }

    public Specification<T> maintenanceStatus(Status.Maintenance[] maintenanceStatus) {
        if (maintenanceStatus == null || maintenanceStatus.length == 0) return null;
        return (root, query, builder) -> {
            if (maintenanceStatus.length == 1)
                return builder.equal(root.get("maintenanceStatus"), maintenanceStatus[0]);
            //noinspection RedundantCast
            return root.get("maintenanceStatus").in((Object[]) maintenanceStatus);
        };
    }
}
