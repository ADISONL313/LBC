package com.cqcskj.lbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqcskj.lbc.entity.MailCode;
import com.cqcskj.lbc.entity.User;
import com.cqcskj.lbc.rs.ResultDeal;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {
    //登录服务
    ResultDeal signInByPwdService(User user);
    //验证码登录
    ResultDeal signInByCodeService(MailCode mailCode);

    //验证码发送服务
    ResultDeal sendCodeService(String mail);

    //注册新账号
    ResultDeal signUpService(User user, String code);
    //修改密码
    ResultDeal updatePwdService(User user, String code);

    //管理员登录
    ResultDeal adminSignInService(User user);

    //获取用户列表
    ResultDeal getUserListService(String mail, String realName);

    //修改个人简介
    ResultDeal updateUserIntroductionService(User user);

    ResultDeal updateUserService(MultipartFile[] multipartFile, User user);

    //修改捐赠权限
    ResultDeal passDonationPermissionService(User user);

    ResultDeal passReportPermissionService(User user);

    ResultDeal rejectionUser(User user);
}
