package com.cqcskj.lbc.controller;

import com.cqcskj.lbc.entity.MailCode;
import com.cqcskj.lbc.entity.User;
import com.cqcskj.lbc.rs.ResultDeal;
import com.cqcskj.lbc.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bysj/user")
//@CrossOrigin(origins = {"*"})
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/test")
    public ResultDeal testAPI(@RequestBody User user){
        return ResultDeal.success(user);
    }
    @PostMapping("/signInByPwd")
    //登录接口
    public ResultDeal signInByPwdAPI(@RequestBody User user){
        return userService.signInByPwdService(user);
    }
    //邮件发送接口
    @GetMapping("/code")
    public ResultDeal sendCodeAPI(@RequestParam("mail") String mail){
        return userService.sendCodeService(mail);
    }
    //邮件登录
    @PostMapping("/signInByCode")
    public ResultDeal signInByCodeAPI(@RequestBody MailCode mailCode){
        return userService.signInByCodeService(mailCode);
    }
    @PostMapping("/signUp")
    public ResultDeal signUpAPI(@RequestBody User user,@RequestParam("code")String code){
        return userService.signUpService(user,code);
    }
    @PostMapping("/updatePwd")
    public ResultDeal updatePwdAPI(@RequestBody User user,@RequestParam("code")String code){
        return userService.updatePwdService(user,code);
    }
    @PostMapping("/adminSignIn")
    public ResultDeal adminSignInAPI(@RequestBody User user){
        return userService.adminSignInService(user);
    }
    @GetMapping("/getUser")
    public ResultDeal getUserListAPI(@RequestParam("mail")String mail,@RequestParam("realName")String realName){
        return userService.getUserListService(mail,realName);
    }
    @PostMapping("/updateIntroduction")
    public ResultDeal updateUserIntroductionAPI(@RequestBody User user){
        return userService.updateUserIntroductionService(user);
    }
    @RequestMapping("/updateUser")
    public ResultDeal updateUserAPI(@RequestParam(value="file",required = false)MultipartFile[] multipartFiles,@ModelAttribute User user){
        return userService.updateUserService(multipartFiles,user);
    }
    @PostMapping("/passDonation")
    public ResultDeal passDonationAPI(@RequestBody User user){
        return userService.passDonationPermissionService(user);
    }
    @PostMapping("/passReport")
    public ResultDeal passReportAPI(@RequestBody User user){
        return userService.passReportPermissionService(user);
    }
    @PostMapping("/rejection")
    public ResultDeal rejectionAPI(@RequestBody User user){
        System.out.println(user);
        return userService.rejectionUser(user);
    }
}
