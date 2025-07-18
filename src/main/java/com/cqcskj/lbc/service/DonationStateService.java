package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.DonationState;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface DonationStateService extends IService<DonationState> {
    ResultDeal getDonationStateByDonationIdService(String donationId);
    ResultDeal updateLogisticsStateService(MultipartFile[] multipartFiles, DonationState donationState);
    ResultDeal delStateImgService(String stateId, String fileName,String whatImg);

    ResultDeal updateTransportStateService(MultipartFile[] multipartFiles, DonationState donationState);

    ResultDeal updateArrivalStateService(MultipartFile[] multipartFiles, DonationState donationState);

    ResultDeal updateDistributeStateService(MultipartFile[] multipartFiles, DonationState donationState);
}
