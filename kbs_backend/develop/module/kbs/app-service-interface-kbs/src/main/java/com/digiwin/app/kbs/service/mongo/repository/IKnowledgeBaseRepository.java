package com.digiwin.app.kbs.service.mongo.repository;

import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName KnowledgeBaseRepository
 * @Description 知识库表仓储
 * @Version: v1-知识库迭代一
 **/
public interface IKnowledgeBaseRepository extends MongoRepository<KnowledgeBaseDoc, String> {
}
