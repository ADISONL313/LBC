package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.DonationState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DonationStateMapper extends BaseMapper<DonationState> {
    int deleteDonationStateByDonationIdMapper(String donationId);
    int updateLogisticsStateMapper(DonationState donationState);
    int delStateImgMapper(@Param("stateId") String stateId,@Param("whatImg")String whatImg);
    int updateTransportStateMapper(DonationState donationState);
    int updateArrivalStateMapper(DonationState donationState);
    int updateDistributeStateMapper(DonationState donationState);
}
