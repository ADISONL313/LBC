package com.cqcskj.lbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("help")
public class Help {
    @TableId("help_id")
    private String helpId;
    private String mail;
    private String helpTitle;
    private String helpContent;
    private String phone;
    private String emergencyPhone;
    private int helpStatus;
    private String address;
    private String helpTime;
    private String releaseTime;
}
