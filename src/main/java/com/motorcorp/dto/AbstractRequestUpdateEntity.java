package com.motorcorp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class AbstractRequestUpdateEntity<T extends AbstractTransactionalEntity> extends AbstractRequestEntity<T> {
    private static final long serialVersionUID = 1L;

    public abstract T update(T entity);

}
