package aystzh.github.com.oss.controller;

import aystzh.github.com.oss.common.ResultBody;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.exception.BizException;
import aystzh.github.com.oss.exception.enums.CommonEnum;
import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.StorageInfoResponse;
import aystzh.github.com.oss.service.storage.StorageStrategyContext;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class OssFileInfoController extends BaseController {


    @Resource
    private StorageStrategyContext storageStrategyContext;

    @PostMapping("/upload")
    public ResultBody uploadMaterial(@RequestParam(value = "project") String project, @RequestParam(value = "storeType") StoreTypeEnum storeType,
                                     @RequestParam(value = "files") MultipartFile[] files) throws Exception {
        //验证文件夹规则,不能包含特殊字符
        validateProjectName(project);
        StorageParamsPo storageParamsPo = new StorageParamsPo();
        storageParamsPo.setFiles(files);
        storageParamsPo.setStoreTypeEnum(storeType);
        storageParamsPo.setProject(project);
        List<StorageInfoResponse> fileRespons = storageStrategyContext.getInstance(storeType).upload(storageParamsPo);
        return ResultBody.success(fileRespons);
    }


    @GetMapping("/download")
    public void download(@RequestParam(value = "fileId") String fileId, @RequestParam(value = "storeType") StoreTypeEnum storeType,
                         HttpServletResponse response) throws Exception {
        if (StrUtil.isBlank(fileId)) {
            throw new BizException(CommonEnum.FILE_ID_CAN_NOT_BE_NULL);
        }
        storageStrategyContext.getInstance(storeType).download(fileId, response);
    }
}
