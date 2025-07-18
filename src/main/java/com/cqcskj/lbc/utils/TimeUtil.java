package com.cqcskj.lbc.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class TimeUtil {
    public String getYMD(){
        LocalDate currentDate = LocalDate.now();
        // 创建一个 DateTimeFormatter 对象，指定日期格式为 "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 使用 DateTimeFormatter 对象将 LocalDate 对象格式化为字符串
        return currentDate.format(formatter);
    }
    public String getYMDHMS(){
        LocalDateTime localDateTime = LocalDateTime.now();
        // 定义格式化模式，使用 24 小时制
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化日期和时间并返回
        return localDateTime.format(dateTimeFormatter);
    }
}
