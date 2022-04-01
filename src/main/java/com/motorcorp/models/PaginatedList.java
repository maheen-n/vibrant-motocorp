package com.motorcorp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"list"})
public class PaginatedList<T extends AbstractView> extends CommonsBean {
    private static final long serialVersionUID = 1L;

    private List<T> list;

    private Integer recordsPerPage;

    private long totalRecords;

    private Integer page;

    private Integer totalPages;

    public PaginatedList() {
    }

    public <E extends AbstractTransactionalEntity> PaginatedList(Page<E> ePage, Class<T> aClass) {
        this.list = ePage.getContent().stream().map(i -> i.getView(aClass)).collect(Collectors.toList());
        this.recordsPerPage = ePage.getPageable().getPageSize();
        this.totalRecords = ePage.getTotalElements();
        this.page = ePage.getNumber();
        this.totalPages = ePage.getTotalPages();
    }
}
