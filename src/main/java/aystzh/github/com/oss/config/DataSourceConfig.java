package aystzh.github.com.oss.config;

import aystzh.github.com.jpa.common.dao.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by zhanghuan on 2022/5/9.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"aystzh.github.com"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class DataSourceConfig {
}
