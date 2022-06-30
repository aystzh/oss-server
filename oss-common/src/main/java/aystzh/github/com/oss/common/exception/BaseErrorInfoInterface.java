package aystzh.github.com.oss.common.exception;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
