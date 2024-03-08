package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * sql异常处理（重载实现）
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        String mess = ex.getMessage();

        //返回前端 当前用户名已经存在
        if(mess.contains("Duplicate entry")){

            String split[] = mess.split(" ");
            //Duplicate entry '张三' for key 'employee.idx_username'
            String username = split[2];
            //String error = mess + username + "已存在";
            String error = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(error);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
//        return Result.error(ex.getMessage());
    }
}
