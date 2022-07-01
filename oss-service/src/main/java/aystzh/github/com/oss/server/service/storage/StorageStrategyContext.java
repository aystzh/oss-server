package aystzh.github.com.oss.server.service.storage;

import aystzh.github.com.oss.sdk.enums.StoreTypeEnum;
import cn.hutool.extra.spring.SpringUtil;

import java.util.HashMap;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public class StorageStrategyContext {

    private HashMap<StoreTypeEnum, Class> handlerMap;

    public StorageStrategyContext(HashMap<StoreTypeEnum, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }


    public StorageStrategy getInstance(StoreTypeEnum type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found hander type for " + type.getMessage());
        }
        return (StorageStrategy) SpringUtil.getBean(clazz);
    }
}
