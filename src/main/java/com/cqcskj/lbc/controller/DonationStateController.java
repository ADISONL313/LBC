package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.DonationState;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.DonationStateService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/donationState")
public class DonationStateController {
    @Resource
    private DonationStateService donationStateService;
    @GetMapping("/getDonationState")
    public ResultDeal getDonationStateByDonationIdAPI(@RequestParam("donationId")String donationId){
        return donationStateService.getDonationStateByDonationIdService(donationId);
    }
    @RequestMapping("/updateLogisticsState")
    public ResultDeal updateLogisticsStateAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute DonationState donationState){
        System.out.println(donationState);
        return donationStateService.updateLogisticsStateService(multipartFiles,donationState);
    }
    @DeleteMapping("/delStateImg")
    public ResultDeal delLogisticsStateImgAPI(@RequestParam("stateId")String stateId,@RequestParam("fileName")String fileName,@RequestParam("whatImg")String whatImg){
        return donationStateService.delStateImgService(stateId,fileName,whatImg);
    }
    @RequestMapping("/updateTransportState")
    public ResultDeal updateTransportStateAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute DonationState donationState){
        return donationStateService.updateTransportStateService(multipartFiles,donationState);
    }
    @RequestMapping("/updateArrivalState")
    public ResultDeal updateArrivalStateAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute DonationState donationState){
        return donationStateService.updateArrivalStateService(multipartFiles,donationState);
    }
    @RequestMapping("/updateDistributeState")
    public ResultDeal updateDistributeStateAPI(@RequestParam(value = "file",required = false)MultipartFile[] multipartFiles, @ModelAttribute DonationState donationState){
        return donationStateService.updateDistributeStateService(multipartFiles,donationState);
    }
}
