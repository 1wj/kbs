package com.digiwin.app.kbs.service.mongo.document;

import com.digiwin.app.kbs.service.common.domain.entity.BaseEntity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName TagClassificationDoc
 * @Description 标签类别,索引只预设了主键和租户
 * @Version: v1-知识库迭代一
 **/
@Data
@Document(collection = "kbs_tag_classification")
@CompoundIndexes({
        @CompoundIndex(name = "kbs_tag_classification_index", def = "{_id:1,tenantsid:1}")
})
public class TagClassificationDoc extends BaseEntity {
    /**
     * 标签类别编号
     */
    @Field("tag_classification_no")
    private String tagClassificationNo;

    /**
     * 标签类别名称
     */
    @Field("tag_classification_name")
    private String tagClassificationName;

    /**
     * 父类别编号
     */
    @Field("parent_no")
    private String parentNo;

    /**
     * 标签集合
     */
    @Field("tag_ids")
    private List<ObjectId> tagIds;
    /**
     * 所属知识库
     */
    @Field("relegation_bases")
    private List<String> relegationBases;

    /**
     * 类别描述
     */
    @Field("tag_description")
    private String tagDescription;
}
