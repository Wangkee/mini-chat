package com.wangkee.exceptions;


import com.wangkee.result.ResponseStatusEnum;

/**
 * 优雅的处理异常，统一进行封装
 */
public class GraceException {

    public static void display(ResponseStatusEnum statusEnum) {
        throw new BusinessException(statusEnum);
    }

}
