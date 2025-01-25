package com.wangkee.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class CosOperatorTest {

    @Autowired
    private CosOperator cosOperator;

    @Test
    public void testUpload() throws IOException {
        // 准备本地文件
        File file = new File("C:\\environment\\mq\\rocketmq-all-5.3.0-bin-release\\rocketmq-all-5.3.0-bin-release\\bin\\mqshutdown.cmd");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 将本地文件包装为 MultipartFile
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                       // 参数名称
                file.getName(),               // 文件名
                "image/png",                  // 文件类型
                fileInputStream               // 文件流
        );

        // 调用 uploadFile 方法
        String etag = cosOperator.uploadFile(multipartFile, 123L, "avatar");

        // 打印返回值
        System.out.println("ETag: " + etag);

        // 确保关闭流
        fileInputStream.close();
    }
}