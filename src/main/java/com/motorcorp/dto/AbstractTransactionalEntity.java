package com.motorcorp.dto;

import com.motorcorp.enums.EntityStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractTransactionalEntity extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedOn;
}
