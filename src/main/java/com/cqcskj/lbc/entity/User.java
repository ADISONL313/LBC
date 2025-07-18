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
@TableName("user")
public class User {

    // 联合主键字段1
    @TableId(value = "mail")
    private String mail;

    private String userDetailId;
    private String nickName;
    private String password;
    private int userRole;
    private String realName;
    private String phoneNumber;
    private String gender;
    private String idCard;
    private String contactAddress;
    private String idPhoto;
    private String introduction;
    private Integer donationPermission;
    private Integer reportPermission;
    private String rejectionReason ;
}