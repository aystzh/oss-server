

package aystzh.github.com.oss.sdk.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class StorageInfoResponseDto implements Serializable {
    /***
     * id
     */
    private String fileName;
    /***
     * 在线url地址
     */
    private String filePath;
    /**
     * 存储路径名称
     */
    private String pathDir;
    /**
     * minio桶名称
     */
    private String bucketName;
    /**
     * minio存储对象名称
     */
    private String objectName;
    /**
     * mongodbId
     */
    private String mongodbId;


}
