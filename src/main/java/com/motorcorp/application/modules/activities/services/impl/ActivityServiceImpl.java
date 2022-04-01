package com.motorcorp.application.modules.activities.services.impl;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.activities.dao.ActivityDao;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.user.dao.UserDao;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(Activity activity) {
        if (activity.getUserId() != null) {
            User user = userDao.findById(activity.getUserId());
            if (user == null) {
                log.error("Activity creation failed :: unknown user id {}", activity.getUserId());
                throw new ServiceException(ErrorCodeEnum.USER_NOT_FOUND);
            }
            activity.setUser(user);
        }
        activityDao.save(activity);
    }

    @Override
    public List<ActivityView> getAllPaginated(Type.Category category, Long activityFor, PageRequest p) {
        List<Activity> activities = activityDao.findActivitiesForCategoryById(category, activityFor, p);
        return activities.stream().map(i -> i.getView(ActivityView.class)).collect(Collectors.toList());
    }
}
