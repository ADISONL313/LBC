package com.cqcskj.lbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("activity_signIn")
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySignIn {
    @TableId("signIn_Id")
    private String signInId;
    private String activityId;
    private String mail;
    private Integer signInStatus;
    private String signInTime;
}