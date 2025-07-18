package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.Help;
import com.cqcskj.lbc.entity.Volunteer;
import com.cqcskj.lbc.entity.VolunteerHelp;
import com.cqcskj.lbc.mapper.HelpMapper;
import com.cqcskj.lbc.mapper.VolunteerHelpMapper;
import com.cqcskj.lbc.mapper.VolunteerMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.VolunteerHelpService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VolunteerHelpServiceImpl extends ServiceImpl<VolunteerHelpMapper, VolunteerHelp>implements VolunteerHelpService {
    @Resource
    private  VolunteerHelpMapper volunteerHelpMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private HelpMapper helpMapper;

    @Override
    public ResultDeal assignVolunteerService(VolunteerHelp volunteerHelp){
        String volunteerHelpId=UUID.randomUUID().toString();
        volunteerHelp.setVolunteerHelpId(volunteerHelpId);
        int i=volunteerHelpMapper.insert(volunteerHelp);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            helpMapper.update(new UpdateWrapper<Help>().set("help_status",1).eq("help_id",volunteerHelp.getHelpId()));
            int a=volunteerMapper.update(new UpdateWrapper<Volunteer>().set("state",1).eq("volunteer_id",volunteerHelp.getVolunteerId()));
            if(a==0){
                volunteerHelpMapper.deleteById(volunteerHelpId);
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }
    }
    @Override
    public ResultDeal getAssignVolunteerService(String helpId){
        List list=volunteerHelpMapper.getAssignVolunteerMapper(helpId);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal cancelAssignVolunteerService(String volunteerHelpId,String mail,String helpId){
        int i=volunteerHelpMapper.deleteById(volunteerHelpId);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            Long d=volunteerHelpMapper.selectCount(new QueryWrapper<VolunteerHelp>().eq("help_id",helpId));
            if(d==0){
                helpMapper.update(new UpdateWrapper<Help>().set("help_status",0).eq("help_id",helpId));
            }
            int a=volunteerMapper.update(new UpdateWrapper<Volunteer>().set("state",0).eq("mail",mail));
            if(a==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }
    }
    @Override
    public ResultDeal getVolunteerNowTaskService(String mail){
        List list=volunteerHelpMapper.getVolunteerNowTaskMapper(mail);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal volunteerFinishHelpService(String volunteerHelpId){
        int i=volunteerHelpMapper.update(new UpdateWrapper<VolunteerHelp>().set("volunteer_help_state",1).eq("volunteer_help_id",volunteerHelpId));
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getHelpHistoryService(String mail){
        List list=volunteerHelpMapper.getHelpHistoryMapper(mail);
        return ResultDeal.success(list);
    }
}
