package com.motorcorp.application.modules.customer.dao;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.dto.AbstractRepositorySpecification;
import com.motorcorp.dto.AbstractTransactionalEntity;
import com.motorcorp.enums.EntityStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.get("name"), contains(expression)),
                builder.like(root.get("phone"), contains(expression)),
                builder.like(root.get("alternativePhone"), contains(expression)),
                builder.like(root.get("email"), contains(expression))
        );
    }
}
