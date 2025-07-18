package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.HelpRecord;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface HelpRecordService extends IService<HelpRecord> {
    ResultDeal addHelpRecordService(MultipartFile[] multipartFiles, HelpRecord helpRecord);

    ResultDeal delFileService(String fileId, String fileName);

    ResultDeal updateHelpRecordService(MultipartFile[] multipartFiles, HelpRecord helpRecord);

    ResultDeal getHelpRecordService(String mail, String helpId);

    ResultDeal delHelpRecordService(String helpRecordId);
}
