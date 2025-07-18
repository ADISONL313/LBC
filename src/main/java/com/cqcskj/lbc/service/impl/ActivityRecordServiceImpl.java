package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.ActivityRecord;
import com.cqcskj.lbc.mapper.ActivityRecordMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.ActivityRecordService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityRecordServiceImpl extends ServiceImpl<ActivityRecordMapper, ActivityRecord> implements ActivityRecordService {
    @Resource
    private ActivityRecordMapper activityRecordMapper;
    @Override
    public ResultDeal getActivityRecordService(String activityId){
        List<ActivityRecord> list= activityRecordMapper.selectList(new QueryWrapper<ActivityRecord>().orderByAsc("record_time").eq("activity_id",activityId));
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal startRecordService(ActivityRecord activityRecord){
        activityRecord.setRecordImage("");
        activityRecord.setActivityRecordId(UUID.randomUUID().toString());
        int i=activityRecordMapper.insert(activityRecord);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Resource
    private MinIOUtil minIOUtil;
    @Override
    public ResultDeal addActivityRecordService(MultipartFile[] multipartFiles, ActivityRecord activityRecord){
        if(multipartFiles==null){
            int i= activityRecordMapper.insert(activityRecord);
            if(i==0){
                return ResultDeal.error("添加失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String tf= minIOUtil.uploadFiles(multipartFiles,"activityrecord",activityRecord.getActivityRecordId());
            if(tf.equals("错误")){
                return ResultDeal.error("添加失败");
            }else {
                activityRecord.setRecordImage(tf);
                int i=activityRecordMapper.insert(activityRecord);
                if(i==0){
                    return ResultDeal.error("添加失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal delFileService(String fileId, String fileName){
        boolean tf= minIOUtil.deleteFile("activityrecord",fileId+"/"+fileName);
        if(tf){
            ActivityRecord activityRecord= activityRecordMapper.selectById(fileId);
            activityRecord.setRecordImage(activityRecord.getRecordImage().replace(fileName+";",""));
            int i=activityRecordMapper.updateById(activityRecord);
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
    public ResultDeal updateActivityRecordService(MultipartFile[] multipartFiles, ActivityRecord activityRecord){
        System.out.println(activityRecord);
        ActivityRecord verifyActivityRecord=activityRecordMapper.selectById(activityRecord.getActivityRecordId());
        if(multipartFiles==null){
            activityRecord.setRecordImage(verifyActivityRecord.getRecordImage());
            int i=activityRecordMapper.updateById(activityRecord);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {

            String tf=minIOUtil.uploadFiles(multipartFiles,"activityrecord",activityRecord.getActivityRecordId());
            if(tf.equals("失败")){
                return ResultDeal.error("修改失败");
            }else {
                activityRecord.setRecordImage(tf+verifyActivityRecord.getRecordImage());
                int i=activityRecordMapper.updateById(activityRecord);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal delActivityRecordService(String activityRecordId){
        try {
            boolean tf =minIOUtil.delFolder("activityrecord",activityRecordId);
            if(tf){
                int i=activityRecordMapper.deleteById(activityRecordId);
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
}
