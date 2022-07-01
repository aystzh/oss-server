package aystzh.github.com.oss.server.controller;

import aystzh.github.com.oss.common.dto.ResultBody;
import aystzh.github.com.oss.common.exception.BizException;
import aystzh.github.com.oss.common.exception.enums.CommonEnum;
import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import aystzh.github.com.oss.sdk.dto.StorageInfoResponseDto;
import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import aystzh.github.com.oss.server.service.storage.StorageStrategyContext;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class OssFileInfoController {


    @Resource
    private StorageStrategyContext storageStrategyContext;

    @PostMapping("/upload")
    public ResultBody uploadMaterial(StorageInfoRequestDto requestDto) throws Exception {
        String storeType = requestDto.getStoreType();
        if (StrUtil.isBlank(storeType)) {
            throw new BizException(CommonEnum.STORE_TYPE_IS_NULL);
        }
        StoreTypeEnum storeTypeEnum = StoreTypeEnum.get(storeType);
        List<StorageInfoResponseDto> fileRespond = storageStrategyContext.getInstance(storeTypeEnum).upload(requestDto);
        return ResultBody.success(fileRespond);
    }


    @GetMapping("/download")
    public void download(@RequestParam(value = "fileId") String fileId, @RequestParam(value = "storeType") String storeType,
                         HttpServletResponse response) throws Exception {
        if (StrUtil.isBlank(fileId)) {
            throw new BizException(CommonEnum.FILE_ID_CAN_NOT_BE_NULL);
        }
        if (StrUtil.isBlank(storeType)) {
            throw new BizException(CommonEnum.STORE_TYPE_IS_NULL);
        }
        StoreTypeEnum storeTypeEnum = StoreTypeEnum.get(storeType);
        storageStrategyContext.getInstance(storeTypeEnum).download(fileId, response);
    }
}
