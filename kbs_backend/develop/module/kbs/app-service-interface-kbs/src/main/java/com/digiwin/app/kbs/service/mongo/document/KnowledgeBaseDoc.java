package com.digiwin.app.kbs.service.mongo.document;

import com.digiwin.app.kbs.service.common.domain.entity.BaseEntity;
import com.digiwin.app.kbs.service.util.TenantTokenUtil;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @ClassName KnowledgeBaseDocument
 * @Description 知识库维护-文档,索引只预设了主键和租户
 * @Version: v1-知识库迭代一
 **/
@Data
@Document(collection = "kbs_knowledge_base")
@CompoundIndexes({
        @CompoundIndex(name = "kbs_knowledge_base_index", def = "{_id:1,tenantsid:1}")
})
public class KnowledgeBaseDoc extends BaseEntity {
    /**
     * 知识库编号
     */
    @Field("base_no")
    private String baseNo;

    @Field("base_name")
    private String baseName;

    @Field("base_description")
    private String baseDescription;
    /**
     * 知识类别集合
     */
    @Field("knowledge_classification_ids")
    private List<ObjectId> classificationIds;
    /**
     * 标签类别集合
     */
    @Field("tag_classification_ids")
    private List<ObjectId> tagClassificationIds;

    public Query getUkQuery(ObjectId objectId) {
        Query query = new Query(Criteria.where("_id").is(objectId).and("tenantsid").is(TenantTokenUtil.getTenantSid()));
        return query;
    }
}
