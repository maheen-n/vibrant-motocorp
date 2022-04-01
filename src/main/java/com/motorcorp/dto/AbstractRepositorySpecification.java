package com.motorcorp.dto;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public abstract class AbstractRepositorySpecification<T extends AbstractTransactionalEntity> {

    protected static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

    protected abstract Specification<T> query(String expression);

    protected Specification<T> date(BaseFilter.DateFilter date) {
        if (date == null || !date.isValid()) return null;
        return (root, query, builder) -> {
            return builder.between(root.get("createdOn"),
                    Date.from(Instant.ofEpochMilli(date.getFrom())),
                    Date.from(Instant.ofEpochMilli(date.getTo())));
        };
    }

    public Specification<T> entityStatus(EntityStatus[] status) {
        return (root, query, builder) -> {
            if (status.length == 1) return builder.equal(root.get("status"), status[0]);
            //noinspection RedundantCast
            return root.get("status").in((Object[]) status);
        };
    }

    protected Specification<T> include(Long[] include) {
        if (include == null || include.length == 0) return null;
        return (root, query, builder) -> {
            if (include.length == 1) return builder.equal(root.get("id"), include[0]);
            return root.get("id").in((Object[]) include);
        };
    }

    protected Specification<T> exclude(Long[] exclude) {
        if (exclude == null || exclude.length == 0) return null;
        return (root, query, builder) -> {
            if (exclude.length == 1) return builder.notEqual(root.get("id"), exclude[0]);
            return builder.not(root.get("id").in((Object[]) exclude));
        };
    }
}
