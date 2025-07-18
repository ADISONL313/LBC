package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.ActivityRegistration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface ActivityRegistrationMapper extends BaseMapper<ActivityRegistration> {
    List getUserActivityRegistrationMapper(@Param("mail")String mail, @Param("activityStartTime")String activityStartTime, @Param("activityEndTime")String activityEndTime, @Param("activityName") String activityName,@Param("activityId")String activityId);
    int rejectionArMapper(ActivityRegistration activityRegistration);
    int passUserArMapper(@Param("mail")String mail,@Param("activityId")String activityId);
}
