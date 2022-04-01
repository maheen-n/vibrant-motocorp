package com.motorcorp.application.modules.vehicles.dao;

import com.motorcorp.application.enums.Type;
import com.motorcorp.dto.AbstractRepositorySpecification;
import com.motorcorp.dto.AbstractTransactionalEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecifications<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.get("name"), contains(expression)),
                builder.like(root.get("registrationNumber"), contains(expression)),
                builder.like(root.get("chassisNumber"), contains(expression)),
                builder.like(root.get("engineNumber"), contains(expression)),
                builder.like(root.get("manufactureYear"), contains(expression)),
                builder.like(root.join("vehicleModel").get("name"), contains(expression)),
                builder.like(root.join("customer").get("name"), contains(expression)),
                builder.like(root.join("customer").get("phone"), contains(expression)),
                builder.like(root.join("customer").get("alternativePhone"), contains(expression)),
                builder.like(root.join("customer").get("pinCode"), contains(expression))
        );
    }

    public Specification<T> customerId(Long customerId) {
        if (customerId == null) return null;
        return (root, query, builder) -> {
            //noinspection RedundantCast
            return builder.equal(root.get("customerId"), customerId);
        };
    }
}
