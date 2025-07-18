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
@TableName("volunteer")
public class Volunteer {
    @TableId("volunteer_id")
    private String volunteerId;
    private String mail;
    private Integer state = 0;
    private Integer exStatus = 0;
    private String volunteerTime;
    private String rejectReason;
}
