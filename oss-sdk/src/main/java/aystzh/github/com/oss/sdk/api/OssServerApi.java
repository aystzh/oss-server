package aystzh.github.com.oss.sdk.api;

import aystzh.github.com.oss.common.dto.ResultBody;
import aystzh.github.com.oss.sdk.config.FeignSupportConfig;
import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhanghuan on 2022/6/30.
 */
@Component
@FeignClient(value = "base-oss-server/oss/file", configuration = FeignSupportConfig.class)
public interface OssServerApi {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultBody uploadMaterial(StorageInfoRequestDto requestDto) throws Exception;

    @GetMapping("/download")
    void download(@RequestParam(value = "fileId") String fileId, @RequestParam(value = "storeType") String storeType,
                  HttpServletResponse response) throws Exception;
}
