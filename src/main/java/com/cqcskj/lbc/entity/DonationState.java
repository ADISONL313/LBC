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
@TableName("donation_state")
public class DonationState {
    @TableId("state_id")
    private String stateId;
    private String donationId;
    private Integer logisticsState;
    private String logisticsTime;
    private String logisticsImg;
    private String logisticsText;
    private Integer transportState;
    private String transportTime;
    private String transportAddress;
    private String transportText;
    private String transportImg;
    private Integer arrivalState;
    private String arrivalTime;
    private String arrivalText;
    private String arrivalImg;
    private Integer distributeState;
    private String distributeTime;
    private String distributeText;
    private String distributeImg;
}