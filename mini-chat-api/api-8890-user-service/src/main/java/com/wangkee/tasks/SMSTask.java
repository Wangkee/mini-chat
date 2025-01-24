package com.wangkee.tasks;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.wangkee.utils.SMSUtils;

@Component
@Slf4j
public class SMSTask {
    @Resource
    private SMSUtils smsUtils;

    @Async
    public void sendSMSInTask(String mobile, String code) throws Exception {
        SMSUtils.sendSMS(mobile, code);
    }
}
