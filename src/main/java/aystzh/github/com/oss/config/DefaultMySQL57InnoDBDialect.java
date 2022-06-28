package aystzh.github.com.oss.config;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * Created by zhanghuan on 2022/6/25.
 */
public class DefaultMySQL57InnoDBDialect extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
