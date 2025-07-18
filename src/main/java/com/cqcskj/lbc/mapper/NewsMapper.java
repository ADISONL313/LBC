package com.cqcskj.lbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqcskj.lbc.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
    List<News> adminGetNewsMapper(@Param("selKey")String key,@Param("newTime")String newTime,@Param("mail")String mail);
    List<News> userGetNewsMapper(@Param("mail") String mail,@Param("newId") String newId,@Param("newTime") String newTime,@Param("selKey") String selKey);
}
