package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.Donation;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface DonationService extends IService<Donation> {
    ResultDeal donationItemService(MultipartFile[] multipartFiles, Donation donation);

    ResultDeal getDonationItemService(String mail, String itemType);

    ResultDeal cancelDonationService(String donationId);

    ResultDeal delDonationFileService(String fileId, String fileName);

    ResultDeal updateDonationService(MultipartFile[] multipartFiles, Donation donation);

    ResultDeal passDonationService(Donation donation);

    ResultDeal rejectionDonationService(Donation donation);

    ResultDeal donationStatisticsService(String mail);
}
