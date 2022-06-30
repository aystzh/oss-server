package aystzh.github.com.oss.sdk.api;

import aystzh.github.com.oss.common.dto.ResultBody;
import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhanghuan on 2022/6/30.
 */
@Component
@FeignClient(value = "base-oss-server")
public interface OssServerApi {

    @PostMapping("/upload")
    ResultBody uploadMaterial(StorageInfoRequestDto requestDto) throws Exception;

    @GetMapping("/download")
    void download(@RequestParam(value = "fileId") String fileId, @RequestParam(value = "storeType") StoreTypeEnum storeType,
                  HttpServletResponse response) throws Exception;
}
