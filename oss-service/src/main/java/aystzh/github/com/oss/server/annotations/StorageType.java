package aystzh.github.com.oss.server.annotations;


import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;

import java.lang.annotation.*;

/**
 * 语义解析自定义场景注解处理类
 * created by zhanghuan on 2022/5/24.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StorageType {
    /**
     * 存储类型
     */
    StoreTypeEnum value();

}
