package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.ActivityRegistration;
import com.cqcskj.lbc.rs.ResultDeal;

public interface ActivityRegistrationService extends IService<ActivityRegistration> {
    ResultDeal registrationActivityService(ActivityRegistration activityRegistration);

    ResultDeal getIsActivityRegistrationByMailAndActivityIdService(String mail, String activityId);

    ResultDeal cancelActivityRegistrationService(String mail, String activityId);

    ResultDeal getUserActivityRegistrationService(String mail,String activityStartTime,String activityEndTime,String activityName,String activityId);

    ResultDeal rejectionUserARService(ActivityRegistration activityRegistration);

    ResultDeal passUserArService(String mail,String activityId);

    ResultDeal getActivityRegistrationCountService(String mail);
}
