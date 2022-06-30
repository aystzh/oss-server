package aystzh.github.com.oss.server.service.storage;


import aystzh.github.com.oss.sdk.dto.StorageInfoRequestDto;
import aystzh.github.com.oss.sdk.dto.StorageInfoResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public interface StorageStrategy {

    List<StorageInfoResponseDto> upload(StorageInfoRequestDto storageParamsPo) throws Exception;

    void download(String fileId, HttpServletResponse response) throws Exception;
}
