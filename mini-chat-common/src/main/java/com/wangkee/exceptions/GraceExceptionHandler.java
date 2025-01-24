package com.wangkee.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wangkee.result.JSONResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GraceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JSONResult returnMyCustomException(BusinessException e) {
        e.printStackTrace();
        return JSONResult.exception(e.getResponseStatusEnum());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JSONResult returnNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        return JSONResult.errorMap(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public JSONResult returnConstraintViolationException(ConstraintViolationException e) {
        return JSONResult.errorMsg(e.getMessage());
    }

    /**
     * 通用异常处理方法（匹配所有未显式捕获的异常）
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResult handleGenericException(Exception e) {
        return JSONResult.error();
    }


    public Map<String, String> getErrors(BindingResult result) {

        Map<String, String> map = new HashMap<>();

        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError fe : errorList) {
            // 错误所对应的属性字段名
            String field = fe.getField();
            // 错误信息
            String message = fe.getDefaultMessage();

            map.put(field, message);
        }

        return map;
    }

}
