package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.entity.ActivityRegistration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    List<Activity> activityGetMapper(@Param("activityName") String activityName,@Param("activityStartTime")String activityStartTime,@Param("activityEndTime")String activityEndTime);

}
