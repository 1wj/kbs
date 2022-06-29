package com.digiwin.app.kbs.service.mongo.document;

import com.digiwin.app.kbs.service.common.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @ClassName KnowledgeClassificationDoc
 * @Description 知识类别文档,索引只预设了主键和租户
 * @Version: v1-知识库迭代一
 **/
@Data
@Document(collection = "kbs_knowledge_classification")
@CompoundIndexes({
        @CompoundIndex(name = "kbs_knowledge_classification_index", def = "{_id:1,tenantsid:1}")
})
public class KnowledgeClassificationDoc extends BaseEntity {
    /**
     * 知识类别编号
     */
    @Field("classification_no")
    private String classificationNo;

    /**
     * 知识类别名称
     */
    @Field("classification_name")
    private String classificationName;

    /**
     * 父节点
     */
    @Field("parent_no")
    private String parentNo;

    /**
     * 归属知识库主键
     */
    @Field("relegation_base_id")
    private String relegationBaseId;

    /**
     * 所属知识库
     */
    @Field("relegation_base_name")
    private String relegationBaseName;

    /**
     * 描述
     */
    @Field("classification_description")
    private String classificationDescription;
}
