package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.News;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/news")
public class NewsController {
    @Resource
    private NewsService newsService;
    @PostMapping("/addNew")
    public ResultDeal addNewAPI(@RequestBody News news){
        return newsService.addNewService(news);
    }
    @RequestMapping("/upload")
    public ResultDeal uploadAPI(@RequestParam(value="file",required = false)MultipartFile[] multipartFile,@ModelAttribute News news){
        System.out.println(news);
        return newsService.uploadFileService(multipartFile,news);
    }
    @GetMapping("/adminGetNews")
    public ResultDeal getNewsAPI(@RequestParam("selKey")String selKey,@RequestParam("newTime")String newTime,@RequestParam("mail")String mail){
        return newsService.adminGetNewsService(selKey,newTime,mail);
    }
    @DeleteMapping("/delNew")
    public ResultDeal delNewAPI(@RequestParam("newId")String newId){
        return newsService.delNewsService(newId);
    }
    @PostMapping("/addHit")
    public ResultDeal addNewHitsAPI(@RequestBody News news){
        return newsService.addNewHitsService(news);
    }
    @PostMapping("/rejectionNew")
    public ResultDeal rejectionNewAPI(@RequestBody News news){
        return newsService.rejectionNewService(news);
    }
    @RequestMapping("/updateNew")
    public ResultDeal updateNewAPI(@RequestParam(value = "file",required = false) MultipartFile[] multipartFile,@ModelAttribute News news){
        return newsService.updateNewService(multipartFile,news);
    }
    @DeleteMapping("/delFile")
    public ResultDeal delFileAPI(@RequestParam("fileId")String fileId,@RequestParam("fileName")String fileName){
        return newsService.delFileService(fileId,fileName);
    }
    @GetMapping("/userGetNews")
    public ResultDeal userGetNewsAPI(@RequestParam("mail") String mail,@RequestParam("newId") String newId,@RequestParam("newTime") String newTime,@RequestParam("selKey") String selKey){
        return newsService.userGetNewsService(mail,newId,newTime,selKey);
    }
}
