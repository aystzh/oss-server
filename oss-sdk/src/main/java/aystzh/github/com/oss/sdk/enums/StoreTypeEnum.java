package aystzh.github.com.oss.sdk.enums;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghuan on 2022/6/28.
 */
public enum StoreTypeEnum {

    FAST_DFS("0", "FAST_DFS"),
    LOCAL_STORAGE("1", "LOCAL_STORAGE"),
    MINIO("2", "MINIO"),
    MONGODB("3", "MONGODB");

    private static Logger logger = LoggerFactory.getLogger(StoreTypeEnum.class);

    private String code;
    private String message;

    StoreTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Object LOCK = new Object();
    private static Map<String, StoreTypeEnum> mapAll;

    static {
        synchronized (LOCK) {
            Map<String, StoreTypeEnum> map = new HashMap<>();
            for (StoreTypeEnum type : StoreTypeEnum.values()) {
                map.put(type.getCode(), type);
            }
            mapAll = ImmutableMap.copyOf(map);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static StoreTypeEnum get(String value) {
        try {
            return mapAll.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
