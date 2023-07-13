package cn.valinaa.auction.bean;

import cn.valinaa.auction.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result<T> {

    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;

    //构造私有化
    private Result() { }

    //设置数据,返回对象的方法
    public static<T> Result<T> build(T data,Integer code,String message) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //判断返回结果中是否需要数据
        if(data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        //设置其他值
        result.setCode(code);
        result.setMessage(message);
        //返回设置值之后的对象
        return result;
    }


    //设置数据,返回对象的方法
    public static<T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //判断返回结果中是否需要数据
        if(data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        //返回设置值之后的对象
        return result;
    }

    //成功的方法
    public static<T> Result<T> success(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    //失败的方法
    public static<T> Result<T> failure(T data,ResultCodeEnum resultCodeEnum) {
        return build(data,resultCodeEnum);
    }

    //设置数据,返回对象的方法
    public static<T> Result<T> build(ResultCodeEnum resultCodeEnum) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<>();
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        result.setData(null);
        //返回设置值之后的对象
        return result;
    }

    //成功的方法
    public static<T> Result<T> success() {
        return build(ResultCodeEnum.SUCCESS);
    }

    //失败的方法
    public static<T> Result<T> failure(ResultCodeEnum resultCodeEnum){
        return build(resultCodeEnum);}
    }
