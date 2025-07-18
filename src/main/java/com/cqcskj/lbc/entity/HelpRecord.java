package com.cqcskj.lbc.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("help_record")
public class HelpRecord {
    @TableId("help_record_id")
    private String helpRecordId;
    private String mail;
    private String recordDescription;
    private String recordImage;
    private String recordDate;
    private String helpId;
}