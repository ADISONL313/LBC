package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.Volunteer;
import com.cqcskj.lbc.rs.ResultDeal;

public interface VolunteerService extends IService<Volunteer> {
    ResultDeal replyForVolunteerService(Volunteer volunteer);

    ResultDeal getVolunteerService(String mail);

    ResultDeal passVolunteerService(Volunteer volunteer);

    ResultDeal rejectionVolunteerService(Volunteer volunteer);

    ResultDeal getIdleVolunteerService();
}
