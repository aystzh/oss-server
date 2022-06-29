package aystzh.github.com.oss.service.storage.strategy;

import aystzh.github.com.oss.annotations.StorageType;
import aystzh.github.com.oss.config.MinIoProperties;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.FileResponse;
import aystzh.github.com.oss.service.storage.StorageStrategy;
import aystzh.github.com.oss.utils.MinIoUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@ConditionalOnProperty(
        value = {"oss.platform.minio.enabled"},
        havingValue = "true"
)
@Import({MinIoProperties.class})
@Component
@StorageType(value = StoreTypeEnum.MINIO)
public class MinIoStorageStrategy implements StorageStrategy {

    @Resource
    private MinIoUtils minIoUtils;


    @Override
    public List<FileResponse> upload(StorageParamsPo storageParamsPo) throws Exception {
        log.info("进入minio存储逻辑");
        List<FileResponse> fileResponses = Lists.newArrayList();

        MultipartFile[] files = storageParamsPo.getFiles();
        for (MultipartFile multipartFile : files) {
            // 得到文件流
            final InputStream inputStream = multipartFile.getInputStream();
            // 得到文件名
            final String originalFilename = multipartFile.getOriginalFilename();
            //String objectName = String.format("file/%s/%s", DateUtil.today(), +System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf(".")));
            String objectName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
            log.info("【objectName的名称】：{}", objectName);
            // 把文件放到minio的boots桶里面
            minIoUtils.putObject(objectName, inputStream);
            // 下面可进行存数据库操作
            // 关闭输入流
            inputStream.close();
            FileResponse fileResponse = new FileResponse(originalFilename, objectName, "");
            fileResponses.add(fileResponse);
        }
        return fileResponses;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws Exception {
        
    }

}
