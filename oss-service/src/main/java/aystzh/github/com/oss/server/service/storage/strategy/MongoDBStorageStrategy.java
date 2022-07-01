package aystzh.github.com.oss.server.service.storage.strategy;

import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import aystzh.github.com.oss.sdk.dto.StorageInfoResponseDto;
import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import aystzh.github.com.oss.server.annotations.StorageType;
import aystzh.github.com.oss.server.service.storage.StorageStrategy;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.google.common.collect.Lists;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@ConditionalOnProperty(
        value = {"oss.platform.mongodb.enabled"},
        havingValue = "true"
)
@Import({FdfsClientConfig.class})
@Component
@StorageType(value = StoreTypeEnum.MONGODB)
public class MongoDBStorageStrategy implements StorageStrategy {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Override
    public List<StorageInfoResponseDto> upload(StorageInfoRequestDto storageParamsPo) throws Exception {
        log.info("进入MONGODB存储逻辑");
        List<StorageInfoResponseDto> responses = Lists.newArrayList();
        MultipartFile[] files = storageParamsPo.getFiles();
        for (MultipartFile multipartFile : files) {
            // 获得提交的文件名
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件输入流
            InputStream ins = multipartFile.getInputStream();
            // 获取文件类型
            String contentType = multipartFile.getContentType();
            // 将文件存储到mongodb中
            ObjectId objectId = gridFsTemplate.store(ins, fileName, contentType);
            log.info("保存成功，objectId:" + objectId);
            StorageInfoResponseDto storageInfoResponse = new StorageInfoResponseDto();
            storageInfoResponse.setFileName(fileName);
            storageInfoResponse.setMongodbId(String.valueOf(objectId));
            responses.add(storageInfoResponse);
        }
        return responses;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws Exception {
        log.info("进入MONGODB存储逻辑 准备下载文件....");
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        if (gridFSFile == null) {
            return;
        }

        String fileName = gridFSFile.getFilename().replace(",", "");
        String contentType = gridFSFile.getMetadata().get("_contentType").toString();

        // 通知浏览器进行文件下载
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = resource.getInputStream();
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}
