package aystzh.github.com.oss.server.config;

import aystzh.github.com.oss.server.filter.GlobalRequestMappingFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Configuration
@Data
@ConditionalOnProperty(
        value = {"oss.platform.local.enabled"},
        havingValue = "true"
)
public class LocalStorageConfigInfo implements Serializable {

    @Value(value = "${local.root}")
    private String root;

    @Value(value = "${local.invokingRoot}")
    private String invokingRoot;

    @Bean
    public GlobalRequestMappingFilter globalRequestMappingFilter(){
        return new GlobalRequestMappingFilter();
    }

}
