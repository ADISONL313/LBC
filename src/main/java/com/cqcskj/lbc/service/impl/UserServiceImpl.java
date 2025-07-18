package com.cqcskj.lbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqcskj.lbc.entity.LoginData;
import com.cqcskj.lbc.entity.MailCode;
import com.cqcskj.lbc.entity.User;
import com.cqcskj.lbc.mapper.UserMapper;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.UserService;
import com.cqcskj.lbc.utils.JwtUti;
import com.cqcskj.lbc.utils.MinIOUtil;
import com.cqcskj.lbc.utils.VerifyCodeUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    //密码登录服务
    public ResultDeal signInByPwdService(User user){
        User verifyUser=userMapper.selectById(user.getMail());
        if(verifyUser==null){
            return ResultDeal.error("用户不存在");
        } else if (user.getPassword().equals(verifyUser.getPassword())) {

            Map<String,Object>map=new HashMap<>();
            map.put("mail",user.getMail());
            String token=JwtUti.genToken(map);
            LoginData loginData=new LoginData(verifyUser.getMail(), token,verifyUser.getNickName());
            return ResultDeal.success(loginData);
        }else {
            return ResultDeal.error("密码错误");
        }
    }
    //注册redis
    @Resource
    private RedisTemplate<String,Object>redisTemplate;
    //验证码登录
    @Override
    public ResultDeal signInByCodeService(MailCode mailCode){
        User verifyUser=userMapper.selectById(mailCode.getMail());
        if(verifyUser==null){
            return ResultDeal.error("用户不存在");
        } else{
            String serverCode =redisTemplate.opsForValue().get(mailCode.getMail())+"";
            if(serverCode.equals(mailCode.getCode())){
                Map<String,Object>map=new HashMap<>();
                map.put("mail",verifyUser.getMail());
                String token=JwtUti.genToken(map);
                redisTemplate.delete(verifyUser.getMail());
                LoginData loginData=new LoginData(verifyUser.getMail(), token,verifyUser.getNickName());
                return ResultDeal.success(loginData);
            }else {
                return ResultDeal.error("验证码错误");
            }
        }

    }
    @Resource
    private VerifyCodeUtil verifyCodeUtil;
    @Override
    //验证码发送服务
    public ResultDeal sendCodeService(String mail){
        System.out.println(mail);
        if(mail==null){
            return ResultDeal.error("邮箱地址错误");
        }else {
            //生成验证码
            String serverCode=verifyCodeUtil.outCode();
            try {
                //发送验证码
                verifyCodeUtil.sendEmail(mail,serverCode);
                //将验证码缓存到redis
                redisTemplate.opsForValue().set(mail,serverCode,5, TimeUnit.MINUTES);
            }catch (Exception e){
                return ResultDeal.error("发送失败");
            }
        }
        return ResultDeal.success();
    }
    //注册新账号
    @Override
    public ResultDeal signUpService(User user, String code){
        String browserCode =redisTemplate.opsForValue().get(user.getMail())+"";
        if(browserCode.equals(code)){
            int i=userMapper.userSignUp(user);
            if(i==0){
                return ResultDeal.error("账号已存在");
            }else {
                redisTemplate.delete(user.getMail());
                return ResultDeal.success();
            }
        }else {
            return ResultDeal.error("验证码错误");
        }
    }
    //修改密码
    @Override
    public ResultDeal updatePwdService(User user, String code){
        User user1=userMapper.selectById(user.getMail());
        if(user1==null){
            return ResultDeal.error("用户不存在");
        }else {
            String serverCode=redisTemplate.opsForValue().get(user.getMail())+"";
            if(serverCode.equals(code)){
                int i=userMapper.updateById(user);
                if(i==0){
                    return ResultDeal.error("修改失败");
                }else {
                    redisTemplate.delete(user.getMail());
                    return ResultDeal.success();
                }
            }else {
                return ResultDeal.error("验证码错误");
            }
        }
    }
    @Override
    //管理员登录
    public ResultDeal adminSignInService(User user){
        User verifyUser=userMapper.selectById(user.getMail());
        if(verifyUser==null){
            return ResultDeal.error("用户不存在");
        }else if(verifyUser.getPassword().equals(user.getPassword())){
            Map<String,Object>map=new HashMap<>();
            map.put("mail",user.getMail());
            String token=JwtUti.genToken(map);
            LoginData loginData=new LoginData(verifyUser.getMail(), token,verifyUser.getNickName());
            return ResultDeal.success(loginData);
        }else {
            return ResultDeal.error("密码错误");
        }
    }
    //获取用户列表
    @Override
    public ResultDeal getUserListService(String mail, String realName){
        List<User> list=userMapper.getUserList(mail,realName);
        return ResultDeal.success(list);
    }
    //修改个人简介
    @Override
    public ResultDeal updateUserIntroductionService(User user){
        System.out.println(user);
        int i=userMapper.updateById(user);
        if(i==0){
            return ResultDeal.error("修改失败");
        }else {
            return ResultDeal.success();
        }
    }
    //注册minio服务
    @Resource
    private MinIOUtil minIOUtil;
    @Override
    public ResultDeal updateUserService(MultipartFile[] multipartFile, User user){
        if(multipartFile==null){
            int i=userMapper.updateById(user);
            if(i==0){
                return ResultDeal.error("修改失败");
            }else {
                return ResultDeal.success();
            }
        }else {
            try {
                boolean tf=minIOUtil.delFolder("user",user.getUserDetailId());
                if(tf){
                    String fileName= minIOUtil.uploadFiles(multipartFile,"user",user.getUserDetailId());
                    if(fileName.equals("错误")){
                        return ResultDeal.error("修改失败");
                    }else {
                        fileName=fileName.substring(0,fileName.length()-1);
                        user.setIdPhoto(fileName);
                        int i=userMapper.updateById(user);
                        if(i==0){
                            return ResultDeal.error("修改失败");
                        }else {
                            return ResultDeal.success();
                        }
                    }
                }else {
                    return ResultDeal.error("修改失败");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
    //修改捐赠权限
    @Override
    public ResultDeal passDonationPermissionService(User user){
        user.setDonationPermission(1);
        int i=userMapper.updateById(user);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal passReportPermissionService(User user){
        user.setReportPermission(1);
        int i=userMapper.updateById(user);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
    @Override
    public ResultDeal rejectionUser(User user){
        user.setDonationPermission(2);
        user.setReportPermission(2);
        int i=userMapper.updateById(user);
        if(i==0){
            return ResultDeal.error("失败");
        }else {
            return ResultDeal.success();
        }
    }
}
