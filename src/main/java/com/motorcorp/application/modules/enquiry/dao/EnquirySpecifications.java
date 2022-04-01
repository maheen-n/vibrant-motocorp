package com.motorcorp.application.modules.enquiry.dao;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.dto.AbstractRepositorySpecification;
import com.motorcorp.dto.AbstractTransactionalEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class EnquirySpecifications<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.get("name"), contains(expression)),
                builder.like(root.get("phone"), contains(expression)),
                builder.like(root.get("email"), contains(expression)),
                builder.like(root.get("enquiryFor"), contains(expression))
        );
    }

    public Specification<T> enquiry(Type.Enquiry[] enquiry) {
        if (enquiry == null || enquiry.length == 0) return null;
        return (root, query, builder) -> {
            if (enquiry.length == 1) return builder.equal(root.get("enquiry"), enquiry[0]);
            //noinspection RedundantCast
            return root.get("enquiry").in((Object[]) enquiry);
        };
    }

    public Specification<T> enquiryStatus(Status.Enquiry[] enquiryStatus) {
        if (enquiryStatus == null || enquiryStatus.length == 0) return null;
        return (root, query, builder) -> {
            if (enquiryStatus.length == 1) return builder.equal(root.get("enquiryStatus"), enquiryStatus[0]);
            //noinspection RedundantCast
            return root.get("enquiryStatus").in((Object[]) enquiryStatus);
        };
    }
}
