package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @ClassName RelagationBaseInfoModel 标签类别信息model
 * @Version: v1-知识库迭代一
 **/
@Data
public class TagClassificationInfoModel {
    /**
     * 標籤類別主鍵
     */
    @JsonProperty(value = "tag_classification_id")
    @Field("tag_classification_id")
    private String tagClassificationId;

    /**
     * 標籤類別名稱
     */
    @JsonProperty(value = "tag_classification_name")
    @Field("tag_classification_name")
    private String tagClassificationName;


}
