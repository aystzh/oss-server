package aystzh.github.com.oss.server.utils;

import aystzh.github.com.oss.server.config.MinIoProperties;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

/**
 * MINIO工具类
 * Created by zhanghuan on 2022/6/29.
 */
@Component
@EnableConfigurationProperties(value = {MinIoProperties.class})
@ConditionalOnProperty(prefix = "minio", name = {"endpoint", "accessKey", "secretKey", "bucketName", "port"})
public class MinIoUtils {
    private final MinioClient minioClient;

    private final String bucketName;

    @Autowired
    private MinIoProperties minIoProperties;


    /**
     * 初始化 minio client
     */
    @SneakyThrows
    public MinIoUtils(MinIoProperties minIoProperties) {
        if (!StringUtils.hasText(minIoProperties.getBucketName())) {
            throw new RuntimeException("bucket name can not be empty or null");
        }
        this.bucketName = StringUtils.trimTrailingCharacter(minIoProperties.getBucketName().trim(), '/');
        minioClient = new MinioClient(minIoProperties.getEndpoint(), minIoProperties.getPort(), minIoProperties.getAccessKey(), minIoProperties.getSecretKey());
    }

    /**
     * 判断 bucket 是否存在
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        boolean isExists = minioClient.bucketExists(bucketName);
        if (!isExists) {
            minioClient.makeBucket(bucketName);
        }
    }

    /**
     * 获取所有 Bucket
     *
     * @return
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名称
     * @return
     */
    public boolean objectExists(String objectName) {
        boolean isExists = false;
        try {
            InputStream inputStream = minioClient.getObject(bucketName, objectName);
            if (null != inputStream) {
                isExists = true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取单个文件
     *
     * @param objectName 文件名称
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String objectName) {
        return minioClient.getObject(bucketName, objectName);
    }

    /**
     * 获取文件集合
     *
     * @param bucketN bucket名称
     * @return
     */
    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketN) {
        if (StringUtils.hasText(bucketN)) {
            bucketN = bucketName;
        }
        return minioClient.listObjects(bucketN);
    }

    /**
     * 根据 prefix 获取文件集合
     *
     * @param prefix bucket prefix前缀
     * @return
     */
    @SneakyThrows
    public Iterable<Result<Item>> listObjectByPrefix(String prefix) {
        return minioClient.listObjects(bucketName, prefix);
    }


    /**
     * 获取文件外链
     *
     * @param objectName 文件名称
     * @return
     */
    @SneakyThrows
    public String buildObjectUrl(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            throw new RuntimeException("object name can not be empty or null");
        }

        objectName = StringUtils.trimLeadingCharacter(StringUtils.trimTrailingCharacter(objectName.trim(), '/'), '/');

        String objectUrl = String.format("%s/%s", bucketName, objectName);
        if (StringUtils.hasText(minIoProperties.getEndpoint())) {
            objectUrl = String.format("%s:%s/%s", minIoProperties.getEndpoint(), minIoProperties.getPort(), objectUrl);
        }
        return objectUrl;
    }


    /**
     * 上传文件-InputStream
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, InputStream stream) {
        minioClient.putObject(bucketName, objectName, stream, new PutObjectOptions(stream.available(), -1));
    }

    /**
     * 上传文件-InputStream
     *
     * @param objectName 文件名称
     * @param stream     文件流
     */
    @SneakyThrows
    public void putObject(String objectName, InputStream stream) {
        minioClient.putObject(bucketName, objectName, stream, new PutObjectOptions(stream.available(), -1));
    }

    /**
     * 上传文件
     *
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contentType 类型
     * @return the object url string
     */
    @SneakyThrows
    public String putObject(String objectName, InputStream stream, Long size, String contentType) {

        if (!StringUtils.hasText(objectName)) {
            throw new RuntimeException("object name can not be empty or null");
        }
        minioClient.putObject(bucketName, objectName, stream, new PutObjectOptions(stream.available(), -1));
        return buildObjectUrl(objectName);
    }


    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    @SneakyThrows
    public void removeObject(String bucketName, String objectName) {
        minioClient.removeObject(bucketName, objectName);
    }

    /**
     * 删除文件
     *
     * @param objectName 文件名称
     */
    @SneakyThrows
    public void removeObject(String objectName) {
        minioClient.removeObject(bucketName, objectName);
    }


}
