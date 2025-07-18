package com.cqcskj.lbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("activity_registration")
public class ActivityRegistration {
    @TableId("registration_id")
    private String registrationId;
    private String mail;
    private int registrationStatus;
    private String rejectionReason;
    private String activityId;
}
