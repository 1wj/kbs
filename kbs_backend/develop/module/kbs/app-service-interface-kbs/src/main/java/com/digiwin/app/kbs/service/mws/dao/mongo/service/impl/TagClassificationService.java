package com.digiwin.app.kbs.service.mws.dao.mongo.service.impl;

import com.digiwin.app.kbs.service.mongo.document.TagClassificationDoc;
import com.digiwin.app.kbs.service.mws.dao.mongo.service.ITagClassificationService;
import com.digiwin.app.service.DWServiceContext;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TagClassificationService
 * @Description 标签类别Service-impl
 * @Date 2022/1/19 18:09
 * @Version: v1-知识库迭代一
 **/
@Service
public class TagClassificationService implements ITagClassificationService {

    private final MongoTemplate mongoTemplate;

    public TagClassificationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 获取标签类别列表
     * @param tag_classification_name 標籤類別名稱，tag_description 類別描述
     * @return 实体
     */
    @Override
    public List<TagClassificationDoc> getTagClassificationList(String tag_classification_name, String tag_description) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "tag_classification_no")));

        Criteria criteria = new Criteria();

        Map<String, Object> profile = DWServiceContext.getContext().getProfile();
        Long tenantsid = (Long) profile.get("tenantSid");

        if(tenantsid != 0){
            criteria.and("tenantsid").is(tenantsid);
        }

        if (!StringUtils.isEmpty(tag_classification_name)) {
            criteria.and("tag_classification_name").regex(".*?" + tag_classification_name + ".*", "i");
        }
        if (!StringUtils.isEmpty(tag_description)) {
            criteria.and("tag_description").regex(".*?" + tag_description + ".*", "i");
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, TagClassificationDoc.class);
    }

    @Override
    public TagClassificationDoc insertTagClassification(TagClassificationDoc tagClassificationDoc) {
       /* List<ObjectId> classificationIds = new ArrayList<>();
        tagClassificationDoc.setRelegationBases(classificationIds);
        List<ObjectId> tagIds = new ArrayList<>();
        tagClassificationDoc.setTagIds(tagClassificationDoc.);*/
        mongoTemplate.save(tagClassificationDoc);
        return tagClassificationDoc;
    }

    @Override
    public TagClassificationDoc updateTagClassification(TagClassificationDoc tagClassificationDoc) {
        mongoTemplate.save(tagClassificationDoc);
        return tagClassificationDoc;
    }

    @Override
    public Boolean deleteTagClassification(ObjectId objectId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("_id").is(objectId);
        query.addCriteria(criteria);

        DeleteResult result = mongoTemplate.remove(query,"kbs_tag_classification");
        if(result.getDeletedCount() > 0){
            return true;
        }else {
            return false;
        }
    }
}
