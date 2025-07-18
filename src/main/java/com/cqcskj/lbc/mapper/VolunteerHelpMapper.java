package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.VolunteerHelp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VolunteerHelpMapper extends BaseMapper<VolunteerHelp> {
    List getAssignVolunteerMapper(String helpId);
    List getVolunteerNowTaskMapper(String mail);
    List getHelpHistoryMapper(String mail);
}
