package aystzh.github.com.oss.po;

import aystzh.github.com.oss.enums.StoreTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Data
public class StorageParamsPo implements Serializable {

    private String project;

    private StoreTypeEnum storeTypeEnum;

    private MultipartFile[] files;
}
