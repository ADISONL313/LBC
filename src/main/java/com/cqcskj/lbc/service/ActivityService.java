package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityService extends IService<Activity> {
    ResultDeal addActivityService(Activity activity, MultipartFile[] multipartFiles);

    ResultDeal getActivityService(String activityName,String activityStartTime,String activityEndTime);

    ResultDeal delActivityService(String activityId);

    ResultDeal delFileService(String fileId, String fileName);

    ResultDeal updateActivityService(MultipartFile[] multipartFile, Activity activity);
}
