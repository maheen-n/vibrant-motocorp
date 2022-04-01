package com.motorcorp.application.modules.ticket.dao;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.dto.AbstractRepositorySpecification;
import com.motorcorp.dto.AbstractTransactionalEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecifications<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.get("name"), contains(expression)),
                builder.like(root.get("phone"), contains(expression)),
                builder.like(root.get("email"), contains(expression)),
                builder.like(root.get("ticketFor"), contains(expression))
        );
    }

    public Specification<T> ticketType(Type.Ticket[] ticket) {
        if (ticket == null || ticket.length == 0) return null;
        return (root, query, builder) -> {
            if (ticket.length == 1) return builder.equal(root.get("ticket"), ticket[0]);
            //noinspection RedundantCast
            return root.get("ticket").in((Object[]) ticket);
        };
    }

    public Specification<T> ticketStatus(Status.Ticket[] ticketStatus) {
        if (ticketStatus == null || ticketStatus.length == 0) return null;
        return (root, query, builder) -> {
            if (ticketStatus.length == 1) return builder.equal(root.get("ticketStatus"), ticketStatus[0]);
            //noinspection RedundantCast
            return root.get("ticketStatus").in((Object[]) ticketStatus);
        };
    }
}
