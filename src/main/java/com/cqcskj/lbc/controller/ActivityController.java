package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;
    @RequestMapping("/addActivity")
    public ResultDeal addActivityAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute Activity activity){
        return activityService.addActivityService(activity,multipartFiles);
    }
    @GetMapping("/getActivity")
    public ResultDeal adminGetActivityAPI(@RequestParam("activityName")String activityName,@RequestParam("activityStartTime")String activityStartTime,@RequestParam("activityEndTime")String activityEndTime){
        return activityService.getActivityService(activityName,activityStartTime,activityEndTime);
    }
    @DeleteMapping("/delActivity")
    public ResultDeal delActivityAPI(@RequestParam("activityId")String activityId){
        return activityService.delActivityService(activityId);
    }
    @DeleteMapping("/delFile")
    public ResultDeal delFileAPI(@RequestParam("fileId")String fileId,@RequestParam("fileName")String fileName){
        return activityService.delFileService(fileId,fileName);
    }
    @PostMapping("/updateActivity")
    public ResultDeal updateActivityAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles,@ModelAttribute Activity activity){
        return activityService.updateActivityService(multipartFiles,activity);
    }
}
