package aystzh.github.com.oss.controller;

import aystzh.github.com.oss.common.ResultBody;
import aystzh.github.com.oss.config.MaterialConfigInfo;
import aystzh.github.com.oss.response.FileBinaryResponse;
import aystzh.github.com.oss.service.OssFileInfoService;
import aystzh.github.com.oss.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private OssFileInfoService ossFileInfoService;

    @PostMapping("/upload")
    public ResultBody uploadMaterial(@RequestParam(value = "project") String project, @RequestParam(value = "files") MultipartFile[] files) throws Exception {
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
        List<FileBinaryResponse> fileBinaryResponses = ossFileInfoService.saveAndStore(materialConfigInfo, projectFile, files);
        return ResultBody.success(fileBinaryResponses);
    }


}
