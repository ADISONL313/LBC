package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.Help;
import com.cqcskj.lbc.entity.User;
import com.cqcskj.lbc.entity.Volunteer;
import com.cqcskj.lbc.entity.VolunteerHelp;
import com.cqcskj.lbc.mapper.HelpMapper;
import com.cqcskj.lbc.mapper.UserMapper;
import com.cqcskj.lbc.mapper.VolunteerHelpMapper;
import com.cqcskj.lbc.mapper.VolunteerMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.HelpService;
import com.cqcskj.lbc.utils.TimeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HelpServiceImpl extends ServiceImpl<HelpMapper, Help> implements HelpService {
    @Resource
    private HelpMapper helpMapper;
    @Resource
    private VolunteerHelpMapper volunteerHelpMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private TimeUtil timeUtil;
    @Override
    public ResultDeal addHelpService(Help help){
        help.setHelpId(UUID.randomUUID().toString());
        int i=helpMapper.insert(help);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal getHelpService(Help help){
        Map<SFunction <Help, ?>, Object> map=new HashMap<>();
        map.put(Help::getMail, help.getMail());
        map.put(Help::getReleaseTime, help.getReleaseTime());
        map.put(Help::getHelpTime, help.getHelpTime());
        LambdaQueryWrapper<Help> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.allEq(map,false);
        lambdaQueryWrapper.orderByAsc(Help::getReleaseTime);
        List<Help> list=helpMapper.selectList(lambdaQueryWrapper);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal cancelHelpService(String helpId){
        int i=helpMapper.deleteById(helpId);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal updateHelpService(Help help){
        int i=helpMapper.updateById(help);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal finishHelpService(String helpId){
        int i= helpMapper.update(new UpdateWrapper<Help>().set("help_status",2).set("help_time",timeUtil.getYMDHMS()).eq("help_id",helpId));
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            List<VolunteerHelp> volunteerHelpList= volunteerHelpMapper.selectList(new QueryWrapper<VolunteerHelp>().eq("help_id",helpId));
            if(volunteerHelpList.isEmpty()){
                return ResultDeal.success();
            }else {
                List<Volunteer> volunteerList=new ArrayList<>();
                for (VolunteerHelp volunteerHelp:volunteerHelpList){
                    volunteerMapper.update(new UpdateWrapper<Volunteer>().set("state",0).eq("volunteer_id",volunteerHelp.getVolunteerId()));
                }

                return ResultDeal.success();
            }
        }
    }
}
