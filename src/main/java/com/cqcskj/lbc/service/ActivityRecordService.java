package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.ActivityRecord;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityRecordService extends IService<ActivityRecord> {
    ResultDeal getActivityRecordService(String activityId);

    ResultDeal startRecordService(ActivityRecord activityRecord);

    ResultDeal addActivityRecordService(MultipartFile[] multipartFiles, ActivityRecord activityRecord);

    ResultDeal delFileService(String fileId, String fileName);

    ResultDeal updateActivityRecordService(MultipartFile[] multipartFiles, ActivityRecord activityRecord);

    ResultDeal delActivityRecordService(String activityRecordId);
}
