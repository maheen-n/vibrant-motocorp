package com.motorcorp.application.modules.user.dto;

import com.motorcorp.application.enums.Role;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import java.util.Set;

import static com.motorcorp.application.constants.DbConstants.USER;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = USER)
public class User extends AbstractTransactionalEntity {
    private String name;

    private String email;

    private String phone;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String userImage;

    private String dob;

    private String password;

    @ElementCollection
    private Set<String> scopes;
}
