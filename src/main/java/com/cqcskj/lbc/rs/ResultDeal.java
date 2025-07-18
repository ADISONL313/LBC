package com.cqcskj.lbc.rs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDeal<T> {
    private Integer code;
    private String message;
    private T data;
    public static <E> ResultDeal<E> success(E data){
        return  new ResultDeal<>(700,"成功",data);
    }
    public static ResultDeal success(){
        return new ResultDeal(700,"成功",null);
    }
    public static ResultDeal error(String message){
        return new ResultDeal(701,message,null);
    }
    public static <A> ResultDeal<A> customize(Integer code,String message,A data){
        return new ResultDeal<>(code,message,data);
    }
}
