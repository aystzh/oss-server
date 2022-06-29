package aystzh.github.com.oss.service.storage;

import aystzh.github.com.oss.po.StorageParamsPo;
import aystzh.github.com.oss.response.FileResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public interface StorageStrategy {

    List<FileResponse> upload(StorageParamsPo storageParamsPo) throws Exception;

    void download(String fileId, HttpServletResponse response) throws Exception;
}
