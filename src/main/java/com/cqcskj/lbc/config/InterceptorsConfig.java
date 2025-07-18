package com.cqcskj.lbc.config;

import com.cqcskj.lbc.interceptors.SignVerifyInterceptors;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {
    @Resource
    private SignVerifyInterceptors signVerifyInterceptors;
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        List<String> list=new ArrayList<>();
        list.add("/bysj/user/signInByPwd");
        list.add("/bysj/user/code");
        list.add("/bysj/user/signInByCode");
        list.add("/bysj/user/signUp");
        list.add("/bysj/user/updatePwd");
        list.add("/bysj/user/adminSignIn");
        list.add("/bysj/news/userGetNews");
        list.add("/bysj/message");
        list.add("/bysj/news/userGetNews");
        list.add("/bysj/activity/getActivity");
        list.add("/bysj/activityRecord/getRecord");
        interceptorRegistry.addInterceptor(signVerifyInterceptors).excludePathPatterns(list);
    }
}
