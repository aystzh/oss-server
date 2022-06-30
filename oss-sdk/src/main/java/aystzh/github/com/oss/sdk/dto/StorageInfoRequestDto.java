package aystzh.github.com.oss.sdk.dto;

import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/30.
 */
@Data
public class StorageInfoRequestDto implements Serializable {

    private String projectName;

    private StoreTypeEnum storeType;

    private MultipartFile[] files;

    private String bucketName;


}
