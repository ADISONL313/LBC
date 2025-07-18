package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.ActivitySignIn;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivitySignInService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bysj/activitySignIn")
public class ActivitySignInController {
    @Resource
    private ActivitySignInService activitySignInService;
    @PostMapping("/sendSignIn")
    public ResultDeal sendActivitySignInAPI(@RequestBody ActivitySignIn activitySignIn){
        return activitySignInService.sendActivitySignInService(activitySignIn.getActivityId());
    }
    @GetMapping("/getActivitySignIn")
    public ResultDeal getActivitySignInAPI(@RequestParam("activityId")String activityId,@RequestParam(value = "mail",required = false)String mail){
        return activitySignInService.getActivitySignInService(activityId,mail);
    }
    @PostMapping("/signFor")
    public ResultDeal signForAPI(@RequestBody ActivitySignIn activitySignIn){
        return activitySignInService.signForService(activitySignIn.getSignInId());
    }
}
