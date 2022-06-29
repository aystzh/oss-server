package aystzh.github.com.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghuan on 2022/6/29.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {
    /**
     * 服务地址
     */
    private String endpoint;

    /**
     * 账号
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;

    private Integer port;
}
