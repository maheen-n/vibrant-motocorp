package com.motorcorp.application.modules.activities.dto;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.motorcorp.application.constants.DbConstants.ACTIVITY;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = ACTIVITY)
@AllArgsConstructor
@RequiredArgsConstructor
public class Activity extends AbstractTransactionalEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type.Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type.Activity activity;

    private Long activityFor;

    private Long activityId;

    @Column(name = "fromValue")
    private String from;

    @Column(name = "toValue")
    private String to;

    private String message;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }
}
