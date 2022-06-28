package aystzh.github.com.oss.config;

import aystzh.github.com.oss.annotations.StorageType;
import aystzh.github.com.oss.enums.StoreTypeEnum;
import aystzh.github.com.oss.service.storage.StorageStrategyContext;
import aystzh.github.com.oss.utils.ClassScaner;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * spring bean后置处理器：添加语义解析策略实现到bean容器
 * created by zhanghuan on 2022/5/24.
 */
@Slf4j
@Component
public class StorageStrategyProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACAGE = "aystzh.github.com.oss.service.storage.strategy";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        HashMap<StoreTypeEnum, Class> handlerMap = Maps.newHashMap();
        ClassScaner.scan(HANDLER_PACAGE, StorageType.class).forEach(aClass -> {
            //获取注解中的类型值
            StoreTypeEnum value = aClass.getAnnotation(StorageType.class).value();
            log.info(aClass.getName());
            //将注解当中的类型作为key，class作为value
            handlerMap.put(value, aClass);
        });
        StorageStrategyContext semanticParsingContext = new StorageStrategyContext(handlerMap);
        //默认单例
        configurableListableBeanFactory.registerSingleton(StorageStrategyContext.class.getName(), semanticParsingContext);
    }
}
