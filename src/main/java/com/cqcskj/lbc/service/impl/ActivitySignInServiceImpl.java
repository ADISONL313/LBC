package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.ActivityRegistration;
import com.cqcskj.lbc.entity.ActivitySignIn;
import com.cqcskj.lbc.mapper.ActivityRegistrationMapper;
import com.cqcskj.lbc.mapper.ActivitySignInMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivitySignInService;
import com.cqcskj.lbc.utils.TimeUtil;
import jakarta.annotation.Resource;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivitySignInServiceImpl extends ServiceImpl<ActivitySignInMapper, ActivitySignIn>implements ActivitySignInService {
    @Resource
    private ActivitySignInMapper activitySignInMapper;
    @Resource
    private ActivityRegistrationMapper activityRegistrationMapper;
    @Resource
    private TimeUtil timeUtil;
    @Override
    public ResultDeal sendActivitySignInService(String activityId){
        List<ActivitySignIn> verifyActivitySignIn= activitySignInMapper.selectList(new QueryWrapper<ActivitySignIn>().like("sign_in_time",timeUtil.getYMD()).eq("activity_id",activityId));
        if(verifyActivitySignIn.isEmpty()){
            List<ActivityRegistration> activityRegistrationList= activityRegistrationMapper.selectList(new QueryWrapper<ActivityRegistration>().eq("activity_id",activityId));
            List<ActivitySignIn> activitySignInList=new ArrayList<>();
            for(ActivityRegistration item:activityRegistrationList){
                if(item.getRegistrationStatus()==1){
                    activitySignInList.add(new ActivitySignIn(UUID.randomUUID().toString(),activityId,item.getMail(),0,timeUtil.getYMD()));
                }
            }
            List<BatchResult> list= activitySignInMapper.insert(activitySignInList);
            if(list.isEmpty()){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            return ResultDeal.error("今天已发起过签到了");
        }
    }
    @Override
    public ResultDeal getActivitySignInService(String activityId,String mail){
        List<ActivitySignIn> list= activitySignInMapper.selectList(new QueryWrapper<ActivitySignIn>().eq("activity_id",activityId).eq(mail!=null,"mail",mail));
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal signForService(String signInId){
        int i=activitySignInMapper.update(new UpdateWrapper<ActivitySignIn>().set("sign_in_status",1).eq("signIn_id",signInId));
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
}
