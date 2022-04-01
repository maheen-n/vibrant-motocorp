package com.motorcorp.application.modules.user.dao.impl;

import com.motorcorp.application.modules.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findUserByEmailOrPhone(String email, String phone);

    @Query(value = "SELECT * FROM tbl_user where phone= ?1 and status = 'ACTIVE'", nativeQuery = true)
    Optional<User> findUserByPhone(String phone);

    @Query(value = "SELECT * FROM tbl_user where email= ?1 and status = 'ACTIVE'", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
}
