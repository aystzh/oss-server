package aystzh.github.com.oss.common.exception.enums;


import aystzh.github.com.oss.common.exception.BaseErrorInfoInterface;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public enum CommonEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),

    //validate 异常
    PROJECT_NAME_CAN_NOT_BE_NULL("2001", "项目名称不能为空!"),
    PROJECT_NAME_CAN_NOT_CONTAINS_REGEX("2002", "项目名称不能包含特殊字符!/ /: *?<>|"),
    PROJECT_NAME_CAN_NOT_CONTAINS_CHINESE("2003", "项目名称不能包含中文或者\\s"),
    FILE_ID_CAN_NOT_BE_NULL("2004", "文件id不能为空!"),
    FILE_NOT_EXISTS("2005", "文件不存在!"),
    STORE_TYPE_IS_NULL("2006", "存储类型storeType不能为空!"),

    ;

    /**
     * 错误码
     */
    private String resultCode;

    /**
     * 错误描述
     */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}