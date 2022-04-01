package com.motorcorp.application.dashboard.models;

import com.motorcorp.models.BaseFilter;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.jooq.impl.DSL.field;

public class DashboardFilter extends BaseFilter {

    public List<Condition> getConditions() {
        List<Condition> conditions = new ArrayList<>();
        if (getDateFilter() != null && getDateFilter().isValid()) {
            DateFilter filter = getDateFilter();
            Condition condition = DSL.noCondition();
            condition = condition.and(field("created_on").between(
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(filter.getFrom()),
                            TimeZone.getDefault().toZoneId()),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(filter.getTo()),
                            TimeZone.getDefault().toZoneId())
            ));
            conditions.add(condition);
        }
        return conditions;

    }
}
