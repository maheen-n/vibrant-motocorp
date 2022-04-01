package com.motorcorp.application.modules.activities.dao.impl;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dao.ActivityDao;
import com.motorcorp.application.modules.activities.dto.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityDaoImpl implements ActivityDao {
    @Autowired
    private ActivityRepository activityRepo;

    @Override
    public void save(Activity activity) {
        activityRepo.save(activity);
    }

    @Override
    public List<Activity> findActivitiesForCategoryById(Type.Category category, Long activityFor, Pageable pageRequest) {
        return activityRepo.findByCategoryAndActivityFor(category, activityFor, pageRequest);
    }

    @Override
    public List<Activity> findActivitiesForCategoryByIdAndActivity(Type.Category category, Long activityFor, Type.Activity activity) {
        return activityRepo.findByCategoryAndActivityAndActivityFor(category, activity, activityFor);
    }
}
