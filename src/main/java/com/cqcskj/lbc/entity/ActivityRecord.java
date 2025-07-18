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
@TableName("activity_record")
public class ActivityRecord {

    // 活动记录 ID，使用 VARCHAR(255) 类型，作为主键
    @TableId("activity_RecordId")
    private String activityRecordId;
    private String recordTime;
    private String recordContent;
    private String recordImage;
    private String activityId;
}