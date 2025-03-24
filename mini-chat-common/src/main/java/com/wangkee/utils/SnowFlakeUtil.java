package com.wangkee.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeUtil {

    private static Integer workerId;
    private static Integer datacenterId;

    // 构造器注入配置
    public SnowFlakeUtil(@Value("${snowflake.worker-id}") Integer workerId,
                         @Value("${snowflake.datacenter-id}") Integer datacenterId) {
        SnowFlakeUtil.workerId = workerId;
        SnowFlakeUtil.datacenterId = datacenterId;
    }

    public static Long nextId(){
        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
}
