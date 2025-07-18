package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.ActivityRegistration;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityRegistrationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bysj/AR")
public class ActivityRegistrationController {
    @Resource
    private ActivityRegistrationService activityRegistrationService;
    @PostMapping("/activityRegistration")
    public ResultDeal activityRegistrationAPI(@RequestBody ActivityRegistration activityRegistration){
        return activityRegistrationService.registrationActivityService(activityRegistration);
    }
    @GetMapping("/getIsAR")
    public ResultDeal getIsActivityRegistrationByMailAndActivityIdAPI(@RequestParam("mail")String mail,@RequestParam("activityId")String activityId){
        return activityRegistrationService.getIsActivityRegistrationByMailAndActivityIdService(mail,activityId);
    }
    @DeleteMapping("/delAR")
    public ResultDeal cancelActivityRegistrationAPI(@RequestParam("mail")String mail,@RequestParam("activityId")String activityId){
        return activityRegistrationService.cancelActivityRegistrationService(mail,activityId);
    }
    @GetMapping("/getUserAr")
    public ResultDeal getUserActivityRegistrationAPI(@RequestParam("mail")String mail,@RequestParam("activityStartTime")String activityStartTime,@RequestParam("activityEndTime")String activityEndTime,@RequestParam("activityName")String activityName,@RequestParam("activityId")String activityId){
        return activityRegistrationService.getUserActivityRegistrationService(mail,activityStartTime,activityEndTime,activityName,activityId);
    }
    @PostMapping("/rejectionAR")
    public ResultDeal rejectionUserARAPI(@RequestBody ActivityRegistration activityRegistration){
        return activityRegistrationService.rejectionUserARService(activityRegistration);
    }
    @GetMapping("/passAr")
    public ResultDeal passUserArAPI(@RequestParam("mail")String mail,@RequestParam("activityId")String activityId){
        return activityRegistrationService.passUserArService(mail,activityId);
    }
    @GetMapping("/getActivityRegistrationCount")
    public ResultDeal getActivityRegistrationCountAPI(@RequestParam("mail")String mail){
        return activityRegistrationService.getActivityRegistrationCountService(mail);
    }
}
