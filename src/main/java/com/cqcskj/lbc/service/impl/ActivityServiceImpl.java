package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.Activity;
import com.cqcskj.lbc.entity.News;
import com.cqcskj.lbc.mapper.ActivityMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private MinIOUtil minIOUtil;
    @Override
    public ResultDeal addActivityService(Activity activity, MultipartFile[] multipartFiles){
        if(multipartFiles==null){
            int i= activityMapper.insert(activity);
            if(i==0){
                return ResultDeal.error("添加失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String tf= minIOUtil.uploadFiles(multipartFiles,"activity",activity.getActivityId());
            if(tf.equals("错误")){
                return ResultDeal.error("添加失败");
            }else {
                activity.setActivityImg(tf);
                int i=activityMapper.insert(activity);
                if(i==0){
                    return ResultDeal.error("添加失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal getActivityService(String activityName,String activityStartTime,String activityEndTime){
//        List<Activity> list=activityMapper.selectList(new QueryWrapper<Activity>().like("activity_name",activityName).like("activity_start_time ",activityStartTime ).like("activity_end_time",activityEndTime));
        List<Activity> list=activityMapper.activityGetMapper(activityName,activityStartTime,activityEndTime);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal delActivityService(String activityId){
        try {
            boolean tf =minIOUtil.delFolder("activity",activityId);
            if(tf){
                int i=activityMapper.deleteById(activityId);
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
    public ResultDeal delFileService(String fileId, String fileName){
        boolean tf= minIOUtil.deleteFile("activity",fileId+"/"+fileName);
        if(tf){
            Activity activity= activityMapper.selectById(fileId);
            activity.setActivityImg(activity.getActivityImg().replace(fileName+";",""));
            int i=activityMapper.updateById(activity);
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
    public ResultDeal updateActivityService(MultipartFile[] multipartFile, Activity activity){
        Activity verifyActivity=activityMapper.selectById(activity.getActivityId());
        if(multipartFile==null){
            activity.setActivityImg(verifyActivity.getActivityImg());
            int i=activityMapper.updateById(activity);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {

            String tf=minIOUtil.uploadFiles(multipartFile,"activity",activity.getActivityId());
            if(tf.equals("失败")){
                return ResultDeal.error("修改失败");
            }else {
                activity.setActivityImg(tf+verifyActivity.getActivityImg());
                int i=activityMapper.updateById(activity);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
}
