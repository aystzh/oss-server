package aystzh.github.com.oss.service;

import aystzh.github.com.oss.config.MaterialConfigInfo;
import aystzh.github.com.oss.response.FileBinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public interface OssFileInfoService {


    List<FileBinaryResponse> saveAndStore(MaterialConfigInfo materialConfigInfo, File projectFile, MultipartFile[] files) throws Exception;
}
