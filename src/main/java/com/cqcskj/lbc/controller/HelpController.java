package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.Help;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.HelpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bysj/help")
public class HelpController {
    @Resource
    private HelpService helpService;
    @PostMapping("/addHelp")
    public ResultDeal addHelpAPI(@RequestBody Help help){
        return helpService.addHelpService(help);
    }
    @PostMapping("/getHelp")
    public ResultDeal getHelpAPI(@RequestBody Help help){
        return helpService.getHelpService(help);
    }
    @DeleteMapping("/cancelHelp")
    public ResultDeal cancelHelpAPI(@RequestParam("helpId")String helpId){
        return helpService.cancelHelpService(helpId);
    }
    @PostMapping("/updateHelp")
    public ResultDeal updateHelpAPI(@RequestBody Help help){
        return helpService.updateHelpService(help);
    }
    @PostMapping("/finishHelp")
    public ResultDeal finishHelpAPI(@RequestBody Help help){
        return helpService.finishHelpService(help.getHelpId());
    }
}
