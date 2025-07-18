package com.cqcskj.lbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("activity")
public class Activity {
    @TableId("activity_id")
    private String activityId;
    private String activityName;
    private String activityStartTime;
    private String activityEndTime;
    private String activityContent;
    private String address;
    private Integer participantsNumber;
    private String activityImg;
}
