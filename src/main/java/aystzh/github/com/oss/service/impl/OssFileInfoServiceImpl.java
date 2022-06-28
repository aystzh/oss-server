package aystzh.github.com.oss.service.impl;

import aystzh.github.com.jpa.common.service.impl.AbstractBaseApiServiceImpl;
import aystzh.github.com.oss.common.ThreadLocalHolder;
import aystzh.github.com.oss.config.MaterialConfigInfo;
import aystzh.github.com.oss.entities.OssFileInfoEntity;
import aystzh.github.com.oss.repository.OssFileInfoRepository;
import aystzh.github.com.oss.response.FileBinaryResponse;
import aystzh.github.com.oss.service.OssFileInfoService;
import aystzh.github.com.oss.utils.FileUploadUtils;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@Service
@Transactional
public class OssFileInfoServiceImpl extends AbstractBaseApiServiceImpl implements OssFileInfoService {

    @Resource
    private OssFileInfoRepository ossFileInfoRepository;

    @Override
    public List<FileBinaryResponse> saveAndStore(MaterialConfigInfo materialConfigInfo, File projectDirectory, MultipartFile[] multipartFiles) throws Exception {
        String ip = ServletUtil.getClientIP(ThreadLocalHolder.getRequest(), null);
        //存ossMaterialInfo对象
        List<OssFileInfoEntity> ossMaterialInfos = Lists.newArrayList();
        String root = materialConfigInfo.getRoot();
        File rootFile = new File(root);
        int start = rootFile.getAbsolutePath().length();
        String currentTimeString = DateTime.now().toString("yyyyMMddHHmmss");
        List<FileBinaryResponse> fileBinaryResponses = Lists.newArrayList();
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
            String invokePath = materialConfigInfo.getInvokingRoot();
            if (invokePath.endsWith("/")) {
                invokePath = materialConfigInfo.getInvokingRoot().substring(0, invokePath.lastIndexOf("/"));
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
            FileBinaryResponse fileBinaryResponse = new FileBinaryResponse(uuid, url, storePathBuffer.toString());
            fileBinaryResponses.add(fileBinaryResponse);
            ossMaterialInfos.add(createTargetMaterial(ip, originalName, mediaType, targetFile, fileBinaryResponse));
        }
        ossFileInfoRepository.saveAll(ossMaterialInfos);
        return fileBinaryResponses;
    }

    private OssFileInfoEntity createTargetMaterial(String ip, String originalName, String mediaType, File targetFile, FileBinaryResponse fileBinaryResponse) {
        //添加ossMaterial对象
        OssFileInfoEntity ossMaterialInfo = new OssFileInfoEntity();
        ossMaterialInfo.setOriginalName(originalName);
        ossMaterialInfo.setFromIp(ip);
        ossMaterialInfo.setCreateDate(DateTime.now());
        ossMaterialInfo.setUpdateDate(DateTime.now());
        ossMaterialInfo.setStorePath(fileBinaryResponse.getStore());
        ossMaterialInfo.setUrl(fileBinaryResponse.getUrl());
        ossMaterialInfo.setLen(new BigDecimal(targetFile.length()).intValue());
        ossMaterialInfo.setByteStr(FileUploadUtils.byteToString(ossMaterialInfo.getLen()));
        ossMaterialInfo.setUserId("");
        ossMaterialInfo.setType(mediaType);
        return ossMaterialInfo;
    }
}
