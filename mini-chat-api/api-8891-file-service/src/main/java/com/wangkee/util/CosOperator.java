package com.wangkee.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.wangkee.exceptions.BusinessException;
import com.wangkee.result.ResponseStatusEnum;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class CosOperator {

    @Resource
    private COSClient cosClient;

    @Value("${tencent.cos.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, Long userId, String folder){
        String fileName = handleFileName(file.getOriginalFilename(), String.valueOf(userId), folder);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    fileName,
                    file.getInputStream(),
                    metadata
            );
            cosClient.putObject(putObjectRequest);

            String regionName = cosClient.getClientConfig().getRegion().getRegionName();

            return String.format("https://%s.cos.%s.myqcloud.com/%s", bucketName, regionName, fileName);
        }catch (Exception e){
            throw new BusinessException(ResponseStatusEnum.FILE_UPLOAD_FAILED);
        }

    }

    public String handleFileName(String originalFilename, String userId, String folder)  {
        String suffix = StringUtils.substringAfter(originalFilename, ".");

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return folder + "/" + userId + "/" + uuid + "." + suffix;
    }
}