package aystzh.github.com.oss.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by zhanghuan on 2022/5/28.
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class OssServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssServerApplication.class, args);
    }

}
