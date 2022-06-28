package aystzh.github.com.oss.controller;

import aystzh.github.com.oss.common.ResultBody;
import aystzh.github.com.oss.config.MaterialConfigInfo;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.FileResponse;
import aystzh.github.com.oss.service.storage.StorageStrategyContext;
import aystzh.github.com.oss.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;


/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class OssFileInfoController extends BaseController {

    @Autowired
    private MaterialConfigInfo materialConfigInfo;

    @Resource
    private StorageStrategyContext storageStrategyContext;

    @PostMapping("/upload")
    public ResultBody uploadMaterial(@RequestParam(value = "project") String project, @RequestParam(value = "storeType") StoreTypeEnum storeType,
                                     @RequestParam(value = "files") MultipartFile[] files) throws Exception {
        log.info(materialConfigInfo.getInvokingRoot());
        //验证文件夹规则,不能包含特殊字符
        validateProjectName(project);
        String root = materialConfigInfo.getRoot();
        File file = new File(root);
        FileUploadUtils.createDirectoryQuietly(file);
        StringBuffer path = new StringBuffer();
        path.append(file.getAbsolutePath());
        path.append(File.separator);
        path.append(project);
        log.info("path:{}", path);
        File projectFile = new File(path.toString());
        FileUploadUtils.createDirectoryQuietly(projectFile);
        StorageParamsPo storageParamsPo = new StorageParamsPo();
        storageParamsPo.setFile(projectFile);
        storageParamsPo.setFiles(files);
        storageParamsPo.setMaterialConfigInfo(materialConfigInfo);
        List<FileResponse> fileRespons = storageStrategyContext.getInstance(storeType).saveAndStore(storageParamsPo);
        return ResultBody.success(fileRespons);
    }


}
