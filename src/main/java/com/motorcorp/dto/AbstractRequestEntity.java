package com.motorcorp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.motorcorp.models.CommonsBean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

@Getter
@Setter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractRequestEntity<T extends AbstractTransactionalEntity> extends CommonsBean {
    private static final long serialVersionUID = 1L;

    @Transient
    public T getEntity(String... ignoreProperties) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked") Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(this, entity, ignoreProperties);
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error("Error while creating object", ex);
            return null;
        }
    }

}
