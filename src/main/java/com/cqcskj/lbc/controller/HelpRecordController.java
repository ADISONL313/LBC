package com.cqcskj.lbc.controller;
import com.cqcskj.lbc.entity.HelpRecord;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.HelpRecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/helpRecord")
public class HelpRecordController {
    @Resource
    private HelpRecordService helpRecordService;
    @RequestMapping("/addHelpRecord")
    public ResultDeal addHelpRecordAPI(@RequestParam(value = "file", required = false) MultipartFile[] multipartFiles, @ModelAttribute HelpRecord helpRecord) {
        System.out.println(helpRecord);
        return helpRecordService.addHelpRecordService(multipartFiles, helpRecord);
    }
    @DeleteMapping("/delFile")
    public ResultDeal delFileAPI(@RequestParam("fileId")String fileId,@RequestParam("fileName")String fileName){
        return helpRecordService.delFileService(fileId,fileName);
    }
    @RequestMapping("/updateHelpRecord")
    public ResultDeal updateHelpRecordAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles,@ModelAttribute HelpRecord helpRecord){
        return helpRecordService.updateHelpRecordService(multipartFiles,helpRecord);
    }
    @GetMapping("/getHelpRecord")
    public ResultDeal getHelpRecordAPI(@RequestParam(value = "mail",required = false)String mail,@RequestParam("helpId")String helpId){
        return helpRecordService.getHelpRecordService(mail,helpId);
    }
    @DeleteMapping("/delHelpRecord")
    public ResultDeal delHelpRecordAPI(@RequestParam("helpRecordId")String helpRecordId){
        return helpRecordService.delHelpRecordService(helpRecordId);
    }
}
