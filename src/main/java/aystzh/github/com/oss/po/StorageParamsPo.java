package aystzh.github.com.oss.po;

import aystzh.github.com.oss.config.MaterialConfigInfo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Data
public class StorageParamsPo implements Serializable {

    private MaterialConfigInfo materialConfigInfo;

    private File file;

    private MultipartFile[] files;
}
