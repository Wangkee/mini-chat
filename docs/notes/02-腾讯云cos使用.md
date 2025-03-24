# 02-腾讯云cos使用

## 1.购买cos服务

https://cloud.tencent.com/act/pro/cosagg?fromSource=gwzcw.8439644.8439644.8439644&utm_medium=cpc&utm_id=gwzcw.8439644.8439644.8439644

## 2.创建存储桶

<img src="C:\0wangke\cs\javaprojects\javalearning\mini-chat-dev\docs\notes\imgs\image-20250304195206607.png" alt="image-20250304195206607" style="zoom: 67%;" />

## 3.创建API密钥

https://console.cloud.tencent.com/cam/capi

## 4.引入依赖

```xml
<dependency>
    <groupId>com.qcloud</groupId>
    <artifactId>cos_api</artifactId>
    <version>5.6.54</version>
</dependency>
```

## 5.配置文件

```yaml
tencent:
  cos:
    secretId: xxx
    secretKey: xxx
    region: ap-nanjing
    bucketName: mini-chat-1256268720
```

## 6.配置类

```java
@Configuration
public class CosConfig {

    @Value("${tencent.cos.secretId}")
    private String secretId;

    @Value("${tencent.cos.secretKey}")
    private String secretKey;

    @Value("${tencent.cos.region}")
    private String region;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        return new COSClient(cred, clientConfig);
    }
}
```

## 7.文件上传工具类

```java
@Component
public class CosOperator {

    @Resource
    private COSClient cosClient;

    @Value("${tencent.cos.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, Long userId, String folder){
        String fileName = handleFileName(
            file.getOriginalFilename(), String.valueOf(userId), folder
        );

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

            return String.format(
                "https://%s.cos.%s.myqcloud.com/%s", bucketName, regionName, fileName
            );
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
```

