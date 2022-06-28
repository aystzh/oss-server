package aystzh.github.com.oss.controller;

import aystzh.github.com.oss.exception.BizException;
import aystzh.github.com.oss.exception.enums.CommonEnum;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public class BaseController {

    /***
     * 验证文件夹名称
     * @param projectName 项目名称
     */
    protected void validateProjectName(String projectName) {
        if (StrUtil.isBlank(projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_BE_NULL);
        }
        String regex = "^.*?(\\\\|\\/|\\:|\\*|\\?|\\？|\\\"|\\“|\\”|\\>|\\<|\\|).*";
        if (ReUtil.isMatch(regex, projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_CONTAINS_REGEX);
        }
        //不能包含\s字符
        //不能包含中文
        regex = ".*?[\\u4e00-\\u9fa5\\s].*";
        if (ReUtil.isMatch(regex, projectName)) {
            throw new BizException(CommonEnum.PROJECT_NAME_CAN_NOT_CONTAINS_CHINESE);
        }
    }


}
