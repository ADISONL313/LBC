package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.entity.ActivityRecord;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityRecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/bysj/activityRecord")
public class ActivityRecordController {
    @Resource
    private ActivityRecordService activityRecordService;
    @GetMapping("/getRecord")
    public ResultDeal getActivityRecordAPI(@RequestParam("activityId")String activityId){
        return activityRecordService.getActivityRecordService(activityId);
    }
    @PostMapping("/startRecord")
    public ResultDeal startRecordAPI(@RequestBody ActivityRecord activityRecord){
        return activityRecordService.startRecordService(activityRecord);
    }
    @RequestMapping("/addRecord")
    public ResultDeal addRecordAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute ActivityRecord activityRecord){
        System.out.println(Arrays.toString(multipartFiles)+"1111");
        return activityRecordService.addActivityRecordService(multipartFiles,activityRecord);
    }
    @DeleteMapping("/delFile")
    public ResultDeal delFileAPI(@RequestParam("fileId")String fileId,@RequestParam("fileName")String fileName){
        return activityRecordService.delFileService(fileId,fileName);
    }
    @RequestMapping("/updateActivityRecord")
    public ResultDeal updateActivityRecordAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute ActivityRecord activityRecord){
        return activityRecordService.updateActivityRecordService(multipartFiles,activityRecord);
    }
    @DeleteMapping("/delActivityRecord")
    public ResultDeal delActivityRecordAPI(@RequestParam("activityRecordId")String activityRecordId){
        return activityRecordService.delActivityRecordService(activityRecordId);
    }
}
