package aystzh.github.com.oss.server.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/29.
 */
@Data
@Configuration
@ConditionalOnProperty(
        value = {"oss.platform.minio.enabled"},
        havingValue = "true"
)
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties implements Serializable {
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
