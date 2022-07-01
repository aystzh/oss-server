package aystzh.github.com.oss.sdk.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/30.
 */
@Data
public class StorageInfoRequestDto implements Serializable {

    private String projectName;

    private String storeType;

    @JSONField(serialize = false)
    private MultipartFile[] files;

    private String bucketName;


}
