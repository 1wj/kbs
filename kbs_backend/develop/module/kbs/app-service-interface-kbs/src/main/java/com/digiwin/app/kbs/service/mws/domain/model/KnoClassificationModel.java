package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @ClassName KnoClassificationModel
 * @Version: v1-知识库迭代一
 **/
@Data
public class KnoClassificationModel {
    @JsonProperty(value = "classification_id")
    private String ObjectId;
    /**
     * 知识类别编号
     */
    @JsonProperty(value = "classification_no")
    private String classificationNo;

    /**
     * 知识类别名称
     */
    @JsonProperty(value = "classification_name")
    private String classificationName;

    /**
     * 父节点
     */
    @JsonProperty(value = "parent_no")
    private String parentNo;

    /**
     * 归属知识库主键
     */
    @JsonProperty(value = "relegation_base_id")
    private String relegationBaseId;

    /**
     * 所属知识库名
     */
    @JsonProperty(value = "relegation_base_name")
    private String relegationBaseName;

    /**
     * 描述
     */
    @JsonProperty(value = "classification_description")
    private String classificationDescription;
}
