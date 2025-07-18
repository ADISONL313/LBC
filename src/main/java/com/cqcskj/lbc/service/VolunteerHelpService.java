package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.VolunteerHelp;
import com.cqcskj.lbc.rs.ResultDeal;

public interface VolunteerHelpService extends IService<VolunteerHelp> {
    ResultDeal assignVolunteerService(VolunteerHelp volunteerHelp);

    ResultDeal getAssignVolunteerService(String helpId);

    ResultDeal cancelAssignVolunteerService(String volunteerHelpId,String mail,String helpId);

    ResultDeal getVolunteerNowTaskService(String mail);

    ResultDeal volunteerFinishHelpService(String volunteerHelpId);

    ResultDeal getHelpHistoryService(String mail);
}
