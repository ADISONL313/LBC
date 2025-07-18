package com.cqcskj.lbc.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("volunteer_help")
public class VolunteerHelp {

    @TableId("volunteer_help_id")
    private String volunteerHelpId;
    private String volunteerId;
    private String mail;
    private String helpId;
}
