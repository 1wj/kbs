package com.digiwin.app.kbs.service.mws.dao.mongo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.mongo.document.KnowledgeBaseDoc;
import com.digiwin.app.kbs.service.mws.dao.mongo.service.IKnowledgeBaseService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName KnowledgeBaseService
 * @Description 知识库Service-impl
 * @Date 2022/1/19 18:09
 * @Version: v1-知识库迭代一
 **/
@Service
public class KnowledgeBaseService implements IKnowledgeBaseService {

    private final MongoTemplate mongoTemplate;

    public KnowledgeBaseService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public KnowledgeBaseDoc addKnowledgeClassification(KnowledgeBaseDoc knowledgeBaseDoc) {
        List<ObjectId> classificationIds = new ArrayList<>();
        knowledgeBaseDoc.setClassificationIds(classificationIds);
        List<ObjectId> tagIds = new ArrayList<>();
        knowledgeBaseDoc.setTagClassificationIds(tagIds);
        mongoTemplate.save(knowledgeBaseDoc);
        return knowledgeBaseDoc;
    }
}
