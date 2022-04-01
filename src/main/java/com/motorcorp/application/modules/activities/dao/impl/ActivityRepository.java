package com.motorcorp.application.modules.activities.dao.impl;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCategoryAndActivityFor(Type.Category category, Long activityFor, Pageable pageRequest);

    List<Activity> findByCategoryAndActivityAndActivityFor(Type.Category category, Type.Activity activity, Long activityFor);
}
