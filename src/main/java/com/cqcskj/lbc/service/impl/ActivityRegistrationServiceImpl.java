package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.ActivityRegistration;
import com.cqcskj.lbc.mapper.ActivityRegistrationMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityRegistrationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityRegistrationServiceImpl extends ServiceImpl<ActivityRegistrationMapper, ActivityRegistration> implements ActivityRegistrationService {
    @Resource
    private ActivityRegistrationMapper activityRegistrationMapper;
    @Override
    public ResultDeal registrationActivityService(ActivityRegistration activityRegistration){
        int i=activityRegistrationMapper.insert(activityRegistration);
        if(i==0){
            return ResultDeal.error("报名失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getIsActivityRegistrationByMailAndActivityIdService(String mail, String activityId){
        Long i= activityRegistrationMapper.selectCount(new QueryWrapper<ActivityRegistration>().eq("mail",mail).eq("activity_id",activityId));
        return ResultDeal.success(i);
    }

    @Override
    public ResultDeal cancelActivityRegistrationService(String mail, String activityId){
        int i=activityRegistrationMapper.delete(new QueryWrapper<ActivityRegistration>().eq("mail",mail).eq("activity_id",activityId));
        if(i==0){
            return ResultDeal.error("取消失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getUserActivityRegistrationService(String mail,String activityStartTime,String activityEndTime,String activityName,String activityId){
        List list=activityRegistrationMapper.getUserActivityRegistrationMapper(mail,activityStartTime,activityEndTime,activityName,activityId);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal rejectionUserARService(ActivityRegistration activityRegistration){
        int i=activityRegistrationMapper.rejectionArMapper(activityRegistration);
        if(i==0){
            return ResultDeal.error("驳回失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal passUserArService(String mail,String activityId){
        int i= activityRegistrationMapper.passUserArMapper(mail,activityId);
        if(i==0){
            return ResultDeal.error("通过失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getActivityRegistrationCountService(String mail){
        Long i=activityRegistrationMapper.selectCount(new QueryWrapper<ActivityRegistration>().eq("mail",mail).eq("registration_status",1));
        return ResultDeal.success(i);
    }
}
