package com.digiwin.app.kbs.service.mongo.repository;

import com.digiwin.app.kbs.service.mongo.document.KnowledgeClassificationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName IKnowledgeClassificationRepository
 * @Description 知识分类仓储
 * @Version: v1-知识库迭代一
 **/
public interface IKnowledgeClassificationRepository  extends MongoRepository<KnowledgeClassificationDoc, String> {
}
