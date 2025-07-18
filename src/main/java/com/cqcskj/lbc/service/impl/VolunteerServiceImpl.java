package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.User;
import com.cqcskj.lbc.entity.Volunteer;
import com.cqcskj.lbc.mapper.UserMapper;
import com.cqcskj.lbc.mapper.VolunteerMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.VolunteerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer>implements VolunteerService {
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public ResultDeal replyForVolunteerService(Volunteer volunteer){
        Volunteer verifyVolunteer=volunteerMapper.selectOne(new QueryWrapper<Volunteer>().eq("mail",volunteer.getMail()));
        if(verifyVolunteer!=null){
            int i= volunteerMapper.update(new UpdateWrapper<Volunteer>().set("ex_status",0).eq("mail",verifyVolunteer.getMail()));
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            volunteer.setVolunteerId(UUID.randomUUID().toString());
            int i=volunteerMapper.insert(volunteer);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }
    }
    @Override
    public ResultDeal getVolunteerService(String mail){
        Volunteer volunteer=volunteerMapper.selectOne(new QueryWrapper<Volunteer>().eq("mail",mail));
        return ResultDeal.success(volunteer);
    }
    @Override
    public ResultDeal passVolunteerService(Volunteer volunteer){
        int i=volunteerMapper.update(new UpdateWrapper<Volunteer>().set("ex_status",1).set("volunteer_time",volunteer.getVolunteerTime()).set("reject_reason","").eq("mail",volunteer.getMail()));
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            int a=userMapper.update(new UpdateWrapper<User>().set("user_role",2).eq("mail",volunteer.getMail()));
            if(a==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }
    }
    @Override
    public ResultDeal rejectionVolunteerService(Volunteer volunteer){
        System.out.println(volunteer.getRejectReason());
        int i=volunteerMapper.update(new UpdateWrapper<Volunteer>().set("ex_status",2).set("reject_reason",volunteer.getRejectReason()).eq("mail",volunteer.getMail()));
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getIdleVolunteerService(){
        List list= volunteerMapper.getIdleVolunteerMapper();
        return ResultDeal.success(list);
    }
}
