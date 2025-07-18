package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.entity.Donation;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.DonationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/donation")
public class DonationController {
    @Resource
    private DonationService donationService;
    @PostMapping("/item")
    public ResultDeal donationItemAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute Donation donation){
        return donationService.donationItemService(multipartFiles,donation);
    }
    @GetMapping("/getItem")
    public ResultDeal getDonationItemAPI(@RequestParam(value = "mail",required = false)String mail,@RequestParam(value = "itemType",required = false)String itemType){
        return donationService.getDonationItemService(mail,itemType);
    }
    @DeleteMapping("/cancelItem")
    public ResultDeal cancelDonationAPI(@RequestParam("donationId")String donationId){
        return donationService.cancelDonationService(donationId);
    }
    @DeleteMapping("/delFile")
    public ResultDeal delDonationFileAPI(@RequestParam("fileId")String fileId,@RequestParam("fileName")String fileName){
        return donationService.delDonationFileService(fileId,fileName);
    }
    @PostMapping("/updateItem")
    public ResultDeal updateDonationAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles,@ModelAttribute Donation donation){
        return donationService.updateDonationService(multipartFiles,donation);
    }
    @PostMapping("/passDonation")
    public ResultDeal passDonationAPI(@RequestBody Donation donation){
        return donationService.passDonationService(donation);
    }
    @PostMapping("/rejection")
    public ResultDeal rejectionDonationAPI(@RequestBody Donation donation){
        return donationService.rejectionDonationService(donation);
    }
    @GetMapping("/donationStatistics")
    public ResultDeal donationStatisticsAPI(@RequestParam("mail")String mail){
        return donationService.donationStatisticsService(mail);
    }
}
