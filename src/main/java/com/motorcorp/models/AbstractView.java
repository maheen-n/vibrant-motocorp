package com.motorcorp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.motorcorp.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractView extends CommonsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Date createdOn;

    private Date updatedOn;

    private EntityStatus status;
}