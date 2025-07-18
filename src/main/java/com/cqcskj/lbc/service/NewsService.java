package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.News;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService extends IService<News> {
    ResultDeal addNewService(News news);
    ResultDeal uploadFileService(MultipartFile[] multipartFiles,News news);

    ResultDeal adminGetNewsService(String selKey, String newTime,String mail);
    ResultDeal delNewsService(String newId);

    ResultDeal addNewHitsService(News news);

    //新闻驳回接口
    ResultDeal rejectionNewService(News news);

    //修改新闻内容接口
    ResultDeal updateNewService(MultipartFile[] multipartFiles, News news);

    ResultDeal delFileService(String fileId,String filName);

    ResultDeal userGetNewsService(String mail, String newId, String newTime, String selKey);
}
