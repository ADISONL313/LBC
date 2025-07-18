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
@TableName("news")
public class News {
    @TableId("new_id")
    private String newId;
    private String newTitle;
    private String newContent ;
    private String newTime;
    private String reporter;
    private String newImg;
    private int hits;
    private String address;
    private String mail;
    private Integer process;
    private String rejectionReason;
}
