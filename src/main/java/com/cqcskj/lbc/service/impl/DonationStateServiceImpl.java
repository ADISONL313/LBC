package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.DonationState;
import com.cqcskj.lbc.mapper.DonationStateMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.DonationStateService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DonationStateServiceImpl extends ServiceImpl<DonationStateMapper, DonationState> implements DonationStateService {
    @Resource
    private DonationStateMapper donationStateMapper;
    @Override
    public ResultDeal getDonationStateByDonationIdService(String donationId){
        List<DonationState> list=donationStateMapper.selectList(new QueryWrapper<DonationState>().eq("donation_id",donationId));
        return ResultDeal.success(list);
    }

    @Resource
    private MinIOUtil minIOUtil;
    @Override
    public ResultDeal updateLogisticsStateService(MultipartFile[] multipartFiles, DonationState donationState){
        if(multipartFiles==null){
            int i= donationStateMapper.updateLogisticsStateMapper(donationState);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String fileName=minIOUtil.uploadFiles(multipartFiles,"donationstate",donationState.getStateId());
            if (fileName.equals("错误")){
                return ResultDeal.error("失败");
            }else {
                donationState.setLogisticsImg(fileName.substring(0,fileName.length()-1));
                int i= donationStateMapper.updateLogisticsStateMapper(donationState);
                if(i==0){
                    return ResultDeal.error("失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal delStateImgService(String stateId, String fileName,String whatImg){
        System.out.println(fileName);
        boolean tf= minIOUtil.deleteFile("donationstate",stateId+"/"+fileName);
        System.out.println(tf);
        if(tf){
            int i=donationStateMapper.delStateImgMapper(stateId,whatImg);
            if(i==0){
                return ResultDeal.error("错误");
            }else {
                return ResultDeal.success();
            }
        }else {
            return ResultDeal.error("错误");
        }
    }
    @Override
    public ResultDeal updateTransportStateService(MultipartFile[] multipartFiles, DonationState donationState){
        if(multipartFiles==null){
            int i= donationStateMapper.updateTransportStateMapper(donationState);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String fileName=minIOUtil.uploadFiles(multipartFiles,"donationstate",donationState.getStateId());
            if (fileName.equals("错误")){
                return ResultDeal.error("失败");
            }else {
                donationState.setTransportImg(fileName.substring(0,fileName.length()-1));
                int i= donationStateMapper.updateTransportStateMapper(donationState);
                if(i==0){
                    return ResultDeal.error("失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal updateArrivalStateService(MultipartFile[] multipartFiles, DonationState donationState){
        if(multipartFiles==null){
            int i= donationStateMapper.updateArrivalStateMapper(donationState);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String fileName=minIOUtil.uploadFiles(multipartFiles,"donationstate",donationState.getStateId());
            if (fileName.equals("错误")){
                return ResultDeal.error("失败");
            }else {
                donationState.setArrivalImg(fileName.substring(0,fileName.length()-1));
                int i= donationStateMapper.updateArrivalStateMapper(donationState);
                if(i==0){
                    return ResultDeal.error("失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }

    @Override
    public ResultDeal updateDistributeStateService(MultipartFile[] multipartFiles, DonationState donationState){
        if(multipartFiles==null){
            int i= donationStateMapper.updateDistributeStateMapper(donationState);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String fileName=minIOUtil.uploadFiles(multipartFiles,"donationstate",donationState.getStateId());
            if (fileName.equals("错误")){
                return ResultDeal.error("失败");
            }else {
                donationState.setDistributeImg(fileName.substring(0,fileName.length()-1));
                int i= donationStateMapper.updateDistributeStateMapper(donationState);
                if(i==0){
                    return ResultDeal.error("失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
}
