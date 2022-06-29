package aystzh.github.com.oss.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

/**
 * Created by zhanghuan on 2022/6/29.
 */
@Configuration
public class MongoDBProperties {

    @Autowired
    private MongoDatabaseFactory mongoDbFactory;

    @Bean
    public GridFSBucket getGridFSBucket() {
        MongoDatabase db = mongoDbFactory.getMongoDatabase();
        return GridFSBuckets.create(db);
    }
}
