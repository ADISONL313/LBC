package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.ActivitySignIn;
import com.cqcskj.lbc.rs.ResultDeal;

public interface ActivitySignInService extends IService<ActivitySignIn> {
    ResultDeal sendActivitySignInService(String activityId);

    ResultDeal getActivitySignInService(String activityId,String mail);

    ResultDeal signForService(String signInId);
}
