package com.digiwin.app.kbs.service.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * @Description: MongoDB集成
 * @Version: v1-知识库迭代一
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableMongoRepositories(basePackages = "com.digiwin.app.kbs.service.mongo.repository")
public class MongoDBConfiguration {
    @Value("${mongodb.database}")
    private String database;
    @Value("${mongodb.connectionString}")
    private String connectionString;
    //最大閒置時間
    @Value("${mongodb.maxConnectionIdleTime:600000}")
    private int maxConnectionIdleTime;
    //最大等待时间
    @Value("${mongodb.maxWaitTime:60000}")
    private int maxWaitTime ;
    @Value("${mongodb.connectTimeout:10000}")
    private int connectTimeout ;
    //每个主机允许的连接数，一个线程变为可用的最大阻塞数
    @Value("${mongodb.connectionsPerHost:100}")
    private int connectionsPerHost ;
    //线程队列数，它以上面connectionsPerHost值相乘的结果就是线程队列最大值
    @Value("${mongodb.threadsAllowedToBlockForConnectionMultiplier:50}")
    private int threadsAllowedToBlockForConnectionMultiplier ;

    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        // 配置
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.maxConnectionIdleTime(maxConnectionIdleTime);
//        builder.connectionsPerHost(connectionsPerHost);
//        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
//        builder.maxWaitTime(maxWaitTime);
//        builder.connectTimeout(connectTimeout);
//        builder.maxConnectionLifeTime(5 * 60 * 1000);
        return new MongoClient(new MongoClientURI(connectionString, builder));
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, database);
    }
}
