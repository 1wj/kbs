package com.digiwin.app.kbs.service.mongo.document;

import com.digiwin.app.kbs.service.common.domain.entity.BaseEntity;
import com.digiwin.app.kbs.service.mws.domain.model.TagClassificationInfoModel;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName TagClassificationDoc
 * @Description 标签,索引只预设了主键和租户
 * @Version: v1-知识库迭代一
 **/
@Data
@Document(collection = "kbs_tag")
@CompoundIndexes({
        @CompoundIndex(name = "kbs_tag_index", def = "{_id:1,tenantsid:1}")
})
public class TagDoc extends BaseEntity {

    /**
     * 标签名称
     */
    @Field("tag_name")
    private String tagName;

    /**
     * 标签描述
     */
    @Field("tag_description")
    private String tagDescription;

    /**
     * 引用次数
     */
    @Field("citations")
    private Integer citations;

    /**
     * 所属类别
     */
    @Field("tag_classifications")
    private List<TagClassificationInfoModel> tagClassifications;

    /**
     * 是否有效 Y.生效;V.失效
     */
    @Field("manage_status")
    private String manageStatus;

}

