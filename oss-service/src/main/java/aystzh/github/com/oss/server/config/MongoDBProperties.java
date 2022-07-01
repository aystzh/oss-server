package aystzh.github.com.oss.server.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.Serializable;

/**
 * Created by zhanghuan on 2022/6/29.
 */
@ConditionalOnProperty(
        value = {"oss.platform.mongodb.enabled"},
        havingValue = "true"
)
@Configuration
public class MongoDBProperties implements Serializable {

    @Value(value = "${mongodb.schema}")
    private String databaseName;

    @Value(value = "${mongodb.uri}")
    private String uri;

    @Value(value = "${mongodb.username}")
    private String userName;

    @Value(value = "${mongodb.password}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        String dbName = "mongodb://" + userName + ":" + password + "@" + uri + "/" + databaseName;

        return MongoClients.create(dbName);
    }


    @Bean
    public GridFSBucket getGridFSBucket() {
        MongoDatabase database = mongoClient().getDatabase(databaseName);
        return GridFSBuckets.create(database);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory dbFactory, MongoConverter converter) {
        return new GridFsTemplate(dbFactory, converter);
    }

}
