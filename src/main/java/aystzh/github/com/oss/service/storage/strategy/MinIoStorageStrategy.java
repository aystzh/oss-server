package aystzh.github.com.oss.service.storage.strategy;

import aystzh.github.com.oss.annotations.StorageType;
import aystzh.github.com.oss.config.MinIoProperties;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.exception.BizException;
import aystzh.github.com.oss.exception.enums.CommonEnum;
import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.StorageInfoResponse;
import aystzh.github.com.oss.service.storage.StorageStrategy;
import aystzh.github.com.oss.utils.MinIoUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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
    public List<StorageInfoResponse> upload(StorageParamsPo storageParamsPo) throws Exception {
        log.info("进入minio存储逻辑");
        List<StorageInfoResponse> responses = Lists.newArrayList();

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
            String url = minIoUtils.buildObjectUrl(objectName);
            StorageInfoResponse storageInfoResponse = new StorageInfoResponse();
            storageInfoResponse.setFileName(originalFilename);
            storageInfoResponse.setObjectName(objectName);
            storageInfoResponse.setFilePath(url);
            responses.add(storageInfoResponse);
        }
        return responses;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws Exception {
        log.info("进入minio存储逻辑 准备下载文件....");
        // 获取"myobject"的输入流。
        InputStream inputStream = minIoUtils.getObject(fileId);
        if (inputStream == null) {
            throw new BizException(CommonEnum.FILE_NOT_EXISTS);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileId, "UTF-8") + "\"");
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}
