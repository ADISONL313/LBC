package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.News;
import com.cqcskj.lbc.mapper.NewsMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.NewsService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Resource
    private NewsMapper newsMapper;
    @Override
    public ResultDeal addNewService(News news){
        if(news==null){
            return ResultDeal.error("提交数据错误");
        }else {
            int i=newsMapper.insert(news);
            if(i==0){
                return ResultDeal.error("添加失败");
            }else {
                return ResultDeal.success("成功");
            }
        }
    }
    @Resource
    private MinIOUtil minIOUtil;
    //文件上传
    @Override
    public ResultDeal uploadFileService(MultipartFile[] multipartFiles,News news){
        if(multipartFiles==null){
            int i=newsMapper.insert(news);
            if(i==0){
                return ResultDeal.error("失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String rs= minIOUtil.uploadFiles(multipartFiles,"news",news.getNewId());
            if(rs.equals("错误")){
                return ResultDeal.error("失败");
            }else {
                news.setNewImg(rs);
                int i=newsMapper.insert(news);
                if(i==0){
                    return ResultDeal.error("失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal adminGetNewsService(String selKey, String newTime,String mail){
        List<News> newsList=newsMapper.adminGetNewsMapper(selKey,newTime,mail);
        return ResultDeal.success(newsList);
    }
    @Override
    public ResultDeal delNewsService(String newId){
        try {
            boolean tf =minIOUtil.delFolder("news",newId);
            if(tf){
                int i=newsMapper.deleteById(newId);
                if(i==0){
                    return ResultDeal.error("删除失败");
                }else {
                    return ResultDeal.success();
                }
            }else {
                return ResultDeal.error("删除失败");
            }
        }catch (Exception e){
            return ResultDeal.error("删除失败");
        }
    }
    @Override
    public ResultDeal addNewHitsService(News news){
        int i=newsMapper.updateById(news);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    //新闻驳回接口
    @Override
    public ResultDeal rejectionNewService(News news){
        int i=newsMapper.updateById(news);
        if(i==0){
            return ResultDeal.error("驳回失败");
        }else {
            return ResultDeal.success();
        }
    }
    //修改新闻内容接口
    @Override
    public ResultDeal updateNewService(MultipartFile[] multipartFile, News news){
        News verifyNews=newsMapper.selectById(news.getNewId());
        if(news.getMail().equals("admin")){
            news.setProcess(1);
        }else {
            news.setProcess(0);
        }
        news.setMail(verifyNews.getMail());
        if(multipartFile==null){
            news.setNewImg(verifyNews.getNewImg());
            int i=newsMapper.updateById(news);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {

            String tf=minIOUtil.uploadFiles(multipartFile,"news",news.getNewId());
            if(tf.equals("失败")){
                return ResultDeal.error("修改失败");
            }else {
                news.setNewImg(tf+verifyNews.getNewImg());
                System.out.println(news.getNewImg());
                int i=newsMapper.updateById(news);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal delFileService(String fileId,String filName){
        boolean tf= minIOUtil.deleteFile("news",fileId+"/"+filName);
        if(tf){
            News news= newsMapper.selectById(fileId);
            news.setNewImg(news.getNewImg().replace(filName+";",""));
            int i=newsMapper.updateById(news);
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
    public ResultDeal userGetNewsService(String mail, String newId, String newTime, String selKey){
        List<News> list=newsMapper.userGetNewsMapper(mail,newId,newTime,selKey);
        return ResultDeal.success(list);
    }
}
