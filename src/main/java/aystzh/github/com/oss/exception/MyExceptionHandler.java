package aystzh.github.com.oss.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e) {
        log.info("未知异常！原因是:" + e.getMessage());
        return e.getMessage();
    }
}
