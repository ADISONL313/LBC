package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.Volunteer;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.VolunteerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bysj/volunteer")
public class VolunteerController {
    @Resource
    private VolunteerService volunteerService;
    @PostMapping("/replyFor")
    public ResultDeal replyForVolunteerAPI(@RequestBody Volunteer volunteer){
        return volunteerService.replyForVolunteerService(volunteer);
    }
    @GetMapping("/getVolunteer")
    public ResultDeal getVolunteerAPI(@RequestParam("mail")String mail){
        return volunteerService.getVolunteerService(mail);
    }
    @PostMapping("/passVolunteer")
    public ResultDeal passVolunteerAPI(@RequestBody Volunteer volunteer){
        return volunteerService.passVolunteerService(volunteer);
    }
    @PostMapping("/rejectionVolunteer")
    public ResultDeal rejectionVolunteerAPI(@RequestBody Volunteer volunteer){
        return volunteerService.rejectionVolunteerService(volunteer);
    }
    @GetMapping("/getIdleVolunteer")
    public ResultDeal getIdleVolunteerAPI(){
        return volunteerService.getIdleVolunteerService();
    }
}
