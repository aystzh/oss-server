package aystzh.github.com.oss.server.service.storage.strategy;

import aystzh.github.com.oss.common.exception.BizException;
import aystzh.github.com.oss.common.exception.enums.CommonEnum;
import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import aystzh.github.com.oss.sdk.dto.StorageInfoResponseDto;
import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import aystzh.github.com.oss.server.annotations.StorageType;
import aystzh.github.com.oss.server.config.LocalStorageConfigInfo;
import aystzh.github.com.oss.server.service.storage.StorageStrategy;
import aystzh.github.com.oss.server.utils.FileUploadUtils;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@ConditionalOnProperty(
        value = {"oss.platform.local.enabled"},
        havingValue = "true"
)
@Component
@StorageType(value = StoreTypeEnum.LOCAL_STORAGE)
public class LocalStorageStrategy implements StorageStrategy {

    @Autowired
    private LocalStorageConfigInfo localStorageConfigInfo;


    @Override
    public List<StorageInfoResponseDto> upload(StorageInfoRequestDto storageParamsPo) throws Exception {
        log.info("进入LOCAL STORAGE存储");
        String projectName = storageParamsPo.getProjectName();
        validateProjectName(projectName);
        String root = localStorageConfigInfo.getRoot();
        File file = new File(root);
        FileUploadUtils.createDirectoryQuietly(file);
        StringBuffer path = new StringBuffer();
        path.append(file.getAbsolutePath());
        path.append(File.separator);
        path.append(projectName);
        log.info("path:{}", path);
        File projectDirectory = new File(path.toString());
        FileUploadUtils.createDirectoryQuietly(projectDirectory);
        MultipartFile[] multipartFiles = storageParamsPo.getFiles();
        File rootFile = new File(root);
        int start = rootFile.getAbsolutePath().length();
        String currentTimeString = DateTime.now().toString("yyyyMMddHHmmss");
        List<StorageInfoResponseDto> fileRespons = Lists.newArrayList();
        //按月目录
        final String monthPath = currentTimeString.substring(0, 6);
        //按日目录,如W020151111
        final String dayPath = currentTimeString.substring(6, 8);
        final String fileDirectory = projectDirectory.getAbsolutePath() + "/" + monthPath + "/" + dayPath;
        log.info("directory:{}", fileDirectory);
        File saveFileDirectory = new File(fileDirectory);
        FileUploadUtils.createDirectoryQuietly(saveFileDirectory);
        for (MultipartFile multpFile : multipartFiles) {
            String originalName = multpFile.getOriginalFilename();
            //文件id
            String uuid = RandomUtil.randomString(12);
            String fileName = uuid;
            String mediaType = "";
            if (originalName.lastIndexOf(".") > 0) {
                mediaType = multpFile.getOriginalFilename().substring(multpFile.getOriginalFilename().lastIndexOf(".") + 1);
                fileName = fileName + "." + mediaType;
            }
            //静态资源存储路径
            StringBuffer storePathBuffer = new StringBuffer();
            String pre = projectDirectory.getAbsolutePath().substring(start);
            pre = FileUploadUtils.transforSysSpec(pre);
            if (!pre.startsWith("/")) {
                storePathBuffer.append("/");
            }
            storePathBuffer.append(pre).append("/")
                    .append(monthPath).append("/").append(dayPath).append("/").append(fileName);
            //判断最后一位是否是/
            String invokePath = localStorageConfigInfo.getInvokingRoot();
            if (invokePath.endsWith("/")) {
                invokePath = localStorageConfigInfo.getInvokingRoot().substring(0, invokePath.lastIndexOf("/"));
            }
            String url = invokePath + storePathBuffer.toString();
            File targetFile = new File(saveFileDirectory.getAbsolutePath() + "/" + fileName);
            FileOutputStream fos = new FileOutputStream(targetFile);
            InputStream ins = multpFile.getInputStream();
            byte[] byts = new byte[1024 * 1024];
            int len = -1;
            while ((len = ins.read(byts)) != -1) {
                fos.write(byts, 0, len);
            }
            //关闭输入输出流
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(ins);
            //不可执行,防止恶意脚本攻击system
            targetFile.setExecutable(false);
            StorageInfoResponseDto storageInfoResponse = new StorageInfoResponseDto();
            storageInfoResponse.setFileName(originalName);
            storageInfoResponse.setFilePath(url);
            storageInfoResponse.setPathDir(storePathBuffer.toString());
            fileRespons.add(storageInfoResponse);
        }
        return fileRespons;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws Exception {
    }


    /***
     * 验证文件夹名称
     * @param projectName 项目名称
     */
    private void validateProjectName(String projectName) {
        if (StrUtil.isBlank(projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_BE_NULL);
        }
        String regex = "^.*?(\\\\|\\/|\\:|\\*|\\?|\\？|\\\"|\\“|\\”|\\>|\\<|\\|).*";
        if (ReUtil.isMatch(regex, projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_CONTAINS_REGEX);
        }
        //不能包含\s字符
        //不能包含中文
        regex = ".*?[\\u4e00-\\u9fa5\\s].*";
        if (ReUtil.isMatch(regex, projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_CONTAINS_CHINESE);
        }
    }

}
