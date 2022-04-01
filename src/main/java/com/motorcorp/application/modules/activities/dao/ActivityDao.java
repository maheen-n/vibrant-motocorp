package com.motorcorp.application.modules.activities.dao;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityDao {
    void save(Activity activity);

    List<Activity> findActivitiesForCategoryById(Type.Category category, Long activityFor, Pageable pageRequest);

    List<Activity> findActivitiesForCategoryByIdAndActivity(Type.Category category, Long activityFor, Type.Activity activity);
}
