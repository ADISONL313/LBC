package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.Donation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DonationMapper extends BaseMapper<Donation> {
    List<Donation> getDonationItemMapper(@Param("mail") String mail,@Param("itemType") String itemType);
    List donationStatisticsMapper(String mail);
}
