package com.motorcorp.dto;

import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
@Slf4j
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Transient
    public <T extends AbstractView> T getView(Class<T> viewClass) {
        try {
            T view = viewClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(this, view);
            return view;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("Error mapping model to view", e);
        }
        return null;
    }

}