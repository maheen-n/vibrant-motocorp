package com.motorcorp.application.modules.activities.services;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.model.ActivityView;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ActivityService {
    void insert(Activity activity);

    List<ActivityView> getAllPaginated(Type.Category category, Long activityFor, PageRequest p);
}
