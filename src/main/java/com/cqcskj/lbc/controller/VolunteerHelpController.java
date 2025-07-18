package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.VolunteerHelp;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.VolunteerHelpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bysj/volunteerHelp")
public class VolunteerHelpController {
    @Resource
    private VolunteerHelpService volunteerHelpService;
    @PostMapping("/assignVolunteer")
    public ResultDeal assignVolunteerAPI(@RequestBody VolunteerHelp volunteerHelp){
        return volunteerHelpService.assignVolunteerService(volunteerHelp);
    }
    @GetMapping("/getAssignVolunteer")
    public ResultDeal getAssignVolunteerAPI(@RequestParam("helpId")String helpId){
        return volunteerHelpService.getAssignVolunteerService(helpId);
    }
    @DeleteMapping("/cancelAssignVolunteer")
    public ResultDeal cancelAssignVolunteerAPI(@RequestParam("volunteerHelpId")String volunteerHelpId,@RequestParam("mail")String mail,@RequestParam("helpId")String helpId){
        return volunteerHelpService.cancelAssignVolunteerService(volunteerHelpId,mail,helpId);
    }
    @GetMapping("/getVolunteerNowTask")
    public ResultDeal getVolunteerNowTaskAPI(@RequestParam("mail")String mail){
        return volunteerHelpService.getVolunteerNowTaskService(mail);
    }
    @PostMapping("/volunteerFinishHelp")
    public ResultDeal volunteerFinishHelpService(@RequestBody VolunteerHelp volunteerHelp){
        return volunteerHelpService.volunteerFinishHelpService(volunteerHelp.getVolunteerHelpId());
    }
    @GetMapping("/getHelpHistory")
    public ResultDeal getHelpHistoryAPI(@RequestParam("mail")String mail){
        return volunteerHelpService.getHelpHistoryService(mail);
    }
}
