package aystzh.github.com.oss.service.storage.strategy;

import aystzh.github.com.oss.annotations.StorageType;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.FileResponse;
import aystzh.github.com.oss.service.storage.StorageStrategy;
import aystzh.github.com.oss.utils.FastdfsUtils;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@ConditionalOnProperty(
        value = {"oss.platform.fdfs.enabled"},
        havingValue = "true"
)
@Import({FdfsClientConfig.class})
@Component
@StorageType(value = StoreTypeEnum.FAST_DFS)
public class FastDFSStorageStrategy implements StorageStrategy {

    @Resource
    private FastdfsUtils fastdfsUtils;

    @Value(value = "${fdfs.http}")
    private String httpPath;

    @Override
    public List<FileResponse> saveAndStore(StorageParamsPo storageParamsPo) throws Exception {
        log.info("进入fastdfs逻辑");
        List<FileResponse> fileResponses = Lists.newArrayList();
        MultipartFile[] files = storageParamsPo.getFiles();
        for (MultipartFile multipartFile : files) {
            String originalFilename = multipartFile.getOriginalFilename();
            StorePath upload = fastdfsUtils.upload(files[0]);
            log.info(upload.getGroup());
            String fullPath = upload.getFullPath();
            String path = upload.getPath();
            String group = upload.getGroup();
            String absolutePath = group + File.separator + path;
            String url = String.format("%s%s%s", httpPath, File.separator, fullPath);
            FileResponse fileResponse = new FileResponse(originalFilename, url, absolutePath);
            fileResponses.add(fileResponse);
        }
        return fileResponses;
    }

}