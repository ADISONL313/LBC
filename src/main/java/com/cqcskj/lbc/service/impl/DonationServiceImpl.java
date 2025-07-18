package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.Donation;
import com.cqcskj.lbc.entity.DonationState;
import com.cqcskj.lbc.mapper.DonationMapper;
import com.cqcskj.lbc.mapper.DonationStateMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.DonationService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class DonationServiceImpl extends ServiceImpl<DonationMapper, Donation> implements DonationService {
    @Resource
    private DonationMapper donationMapper;
    @Resource
    private MinIOUtil minIOUtil;
    @Resource
    private DonationStateMapper donationStateMapper;
    @Override
    public ResultDeal donationItemService(MultipartFile[] multipartFiles, Donation donation){
        DonationState donationState=new DonationState();
        donationState.setStateId(UUID.randomUUID().toString());
        donationState.setDonationId(donation.getDonationId());
        if(multipartFiles==null){
            int i=donationMapper.insert(donation);
            if(i==0){
                return ResultDeal.error("捐赠失败");
            }else {
                int tt= donationStateMapper.insert(donationState);
                if(tt==1){
                    return ResultDeal.success();
                }else {
                    donationMapper.deleteById(donation.getDonationId());
                    return ResultDeal.error("捐赠失败");
                }
            }
        }else {
            String tf= minIOUtil.uploadFiles(multipartFiles,"donation",donation.getDonationId());
            if(tf.equals("错误")){
                return ResultDeal.error("捐赠失败");
            }else {
                donation.setItemImg(tf);
                int i=donationMapper.insert(donation);
                if(i==0){
                    return ResultDeal.error("捐赠失败");
                }else {
                    int tt= donationStateMapper.insert(donationState);
                    if(tt==1){
                        return ResultDeal.success();
                    }else {
                        donationMapper.deleteById(donation.getDonationId());
                        return ResultDeal.error("捐赠失败");
                    }
                }
            }
        }
    }
    @Override
    public ResultDeal getDonationItemService(String mail, String itemType){
        List<Donation> list=donationMapper.getDonationItemMapper(mail,itemType);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal cancelDonationService(String donationId){
        try {
            boolean tf =minIOUtil.delFolder("donation",donationId);
            if(tf){
                int i=donationStateMapper.delete(new QueryWrapper<DonationState>().eq("donation_id",donationId));
                if(i==0){
                    return ResultDeal.error("删除失败");
                }else {
                    int tt=donationMapper.deleteById(donationId);
                    if(tt==1){
                        return ResultDeal.success();
                    }else {
                        return ResultDeal.error("删除失败");
                    }
                }
            }else {
                return ResultDeal.error("删除失败");
            }
        }catch (Exception e){
            return ResultDeal.error("删除失败");
        }
    }
    @Override
    public ResultDeal delDonationFileService(String fileId, String fileName){
        boolean tf= minIOUtil.deleteFile("donation",fileId+"/"+fileName);
        if(tf){
            Donation donation= donationMapper.selectById(fileId);
            donation.setItemImg(donation.getItemImg().replace(fileName+";",""));
            int i=donationMapper.updateById(donation);
            if(i==0){
                return ResultDeal.error("删除失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            return ResultDeal.error("删除失败");
        }
    }
    @Override
    public ResultDeal updateDonationService(MultipartFile[] multipartFiles, Donation donation){
        Donation verifyDonation=donationMapper.selectById(donation.getDonationId());
        if(multipartFiles==null){
            donation.setItemImg(verifyDonation.getItemImg());
            int i=donationMapper.updateById(donation);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {

            String tf=minIOUtil.uploadFiles(multipartFiles,"donation",donation.getDonationId());
            if(tf.equals("失败")){
                return ResultDeal.error("修改失败");
            }else {
                donation.setItemImg(tf+verifyDonation.getItemImg());
                int i=donationMapper.updateById(donation);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal passDonationService(Donation donation){
        int i=donationMapper.updateById(donation);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal rejectionDonationService(Donation donation){
        int i=donationMapper.updateById(donation);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal donationStatisticsService(String mail){
        List list= donationMapper.donationStatisticsMapper(mail);
        return ResultDeal.success(list);
    }
}
