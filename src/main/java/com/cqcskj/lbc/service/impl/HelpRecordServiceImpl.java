package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.HelpRecord;
import com.cqcskj.lbc.mapper.HelpRecordMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.HelpRecordService;
import com.cqcskj.lbc.utils.MinIOUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HelpRecordServiceImpl extends ServiceImpl<HelpRecordMapper, HelpRecord>implements HelpRecordService {
    @Resource
    private HelpRecordMapper helpRecordMapper;
    @Resource
    private MinIOUtil minIOUtil;
    @Override
    public ResultDeal addHelpRecordService(MultipartFile[] multipartFiles, HelpRecord helpRecord){
        if(multipartFiles==null){
            int i= helpRecordMapper.insert(helpRecord);
            if(i==0){
                return ResultDeal.error("添加失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            String tf= minIOUtil.uploadFiles(multipartFiles,"helprecord",helpRecord.getHelpRecordId());
            if(tf.equals("错误")){
                return ResultDeal.error("添加失败");
            }else {
                helpRecord.setRecordImage(tf);
                int i=helpRecordMapper.insert(helpRecord);
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
        boolean tf= minIOUtil.deleteFile("helprecord",fileId+"/"+fileName);
        if(tf){
            HelpRecord helpRecord= helpRecordMapper.selectById(fileId);
            helpRecord.setRecordImage(helpRecord.getRecordImage().replace(fileName+";",""));
            int i=helpRecordMapper.updateById(helpRecord);
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
    public ResultDeal updateHelpRecordService(MultipartFile[] multipartFiles, HelpRecord helpRecord){
        System.out.println(helpRecord);
        HelpRecord verifyHelpRecord=helpRecordMapper.selectById(helpRecord.getHelpRecordId());
        if(multipartFiles==null){
            helpRecord.setRecordImage(verifyHelpRecord.getRecordImage());
            int i=helpRecordMapper.updateById(helpRecord);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {

            String tf=minIOUtil.uploadFiles(multipartFiles,"helprecord",helpRecord.getHelpRecordId());
            if(tf.equals("失败")){
                return ResultDeal.error("修改失败");
            }else {
                helpRecord.setRecordImage(tf+verifyHelpRecord.getRecordImage());
                int i=helpRecordMapper.updateById(helpRecord);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    return ResultDeal.success();
                }
            }
        }
    }
    @Override
    public ResultDeal getHelpRecordService(String mail, String helpId){
        Map<SFunction<HelpRecord, ?>, Object> map=new HashMap<>();
        map.put(HelpRecord::getMail, mail);
        map.put(HelpRecord::getHelpId,helpId);
        LambdaQueryWrapper<HelpRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.allEq(map,false);
        lambdaQueryWrapper.orderByAsc(HelpRecord::getRecordDate);
        List<HelpRecord> list=helpRecordMapper.selectList(lambdaQueryWrapper);
        return ResultDeal.success(list);
    }
    @Override
    public ResultDeal delHelpRecordService(String helpRecordId){
        try {
            boolean tf =minIOUtil.delFolder("helprecord",helpRecordId);
            if(tf){
                int i=helpRecordMapper.deleteById(helpRecordId);
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
