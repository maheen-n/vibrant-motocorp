package com.motorcorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
public class BaseFilter extends CommonsBean {
    private EntityStatus[] status;

    private DateFilter dateFilter;

    @PositiveOrZero
    private int page = 0;

    @Min(1)
    @Max(1000)
    private int rows = 15;

    private String query;

    private Long[] include;

    private Long[] exclude;

    public void setQ(final String q) {
        this.query = q;
    }

    private String sortBy;

    private Sort.Direction sortIn = Sort.Direction.DESC;

    public EntityStatus[] getStatus() {
        return status == null || status.length == 0 ? new EntityStatus[]{EntityStatus.ACTIVE} : status;
    }

    public Sort sort() {
        return Sort.by(new Sort.Order(sortIn, StringUtils.defaultIfBlank(sortBy, "createdOn")));
    }

    public PageRequest filterPageable() {
        return PageRequest.of(getPage(), getRows(), sort());
    }

    @Getter
    @Setter
    public static class DateFilter implements Serializable {
        private String field;

        private Long from;

        private Long to;

        public DateFilter() {
        }

        public DateFilter(String field, Long from, Long to) {
            this.field = field;
            this.from = from;
            this.to = to;
        }

        public DateFilter(Long from, Long to) {
            this.from = from;
            this.to = to;
        }

        @JsonIgnore
        public boolean isValid() {
            return (from != null && to != null && from < to);
        }
    }
}
