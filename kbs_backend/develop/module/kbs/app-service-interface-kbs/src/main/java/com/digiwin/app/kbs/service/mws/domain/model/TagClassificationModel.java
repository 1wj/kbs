package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName TagClassificationModel
 * @Version: v1-知识库迭代一
 **/
@Data
public class TagClassificationModel {
    @JsonProperty("tag_classification_id")
    private String objectId;
    /**
     * 标签类别编号
     */
    @JsonProperty(value = "tag_classification_no")
    private String tagClassificationNo;

    /**
     * 标签类别名称
     */
    @JsonProperty(value = "tag_classification_name")
    private String tagClassificationName;

    /**
     * 父类别编号
     */
    @JsonProperty(value = "parent_no")
    private String parentNo;

    /**
     * 归属知识库信息
     */
    @JsonProperty(value = "relegation_bases_info")
    private List<RelagationBaseInfoModel> relegationBasesInfos;
    /**
     * 标签集合
     *//*
    @JsonProperty(value = "tag_ids")
    private List<ObjectId> tagIds;*/
    /**
     * 类别描述
     */
    @JsonProperty(value = "tag_description")
    private String tagDescription;
}
