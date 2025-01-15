package utils;

import org.springframework.stereotype.Component;

@Component
public class SMSUtils {

    public static void sendSMS(String phone, String code) throws Exception {
        //模拟短信发送
        Thread.sleep(5000);
        System.out.println("sendSMS finish");
    }

}


