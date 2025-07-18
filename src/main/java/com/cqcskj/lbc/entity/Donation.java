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
@TableName("donation")
public class Donation {
    @TableId("donation_id")
    private String donationId;
    private String mail;
    private String itemName;
    private String expressId;
    private String itemImg;
    private String rejectionReason;
    private int auditStatus;
    private String itemType;
    private String address;
}
