package aystzh.github.com.oss.config;

import aystzh.github.com.oss.filter.GlobalRequestMappingFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Configuration
@Data
public class LocalStorageConfigInfo {

    @Value(value = "${local.root}")
    private String root;

    @Value(value = "${local.invokingRoot}")
    private String invokingRoot;

    @Bean
    public GlobalRequestMappingFilter globalRequestMappingFilter(){
        return new GlobalRequestMappingFilter();
    }

}