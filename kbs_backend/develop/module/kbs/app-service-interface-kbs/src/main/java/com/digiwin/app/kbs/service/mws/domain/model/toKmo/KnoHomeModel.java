package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.alibaba.fastjson.annotation.JSONField;
import com.digiwin.app.kbs.service.configuration.annotation.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName KnoHomeModel
 * @Description 知识库检索入参model
 * @Author HeX
 * @Date 2022/2/23 10:26
 * @Version 1.0
 **/
@Data
public class KnoHomeModel implements Serializable {
    /**
     * 检索内容
     */
    @JsonProperty("search_content")
    @NotNull(message = "search_content is null")
    private String searchContent;
    /**
     * 搜索范围
     */
    @JsonProperty("search_range")
    private String searchRange;

    /**
     * 知识类别编号
     */
    @JsonProperty("knowledge_classification_no")
    private String knowledgeClassificationNo;

    /**
     * 知识类别名称
     */
    @JsonProperty("knowledge_classification_name")
    private String knowledgeClassificationName;

    /**
     * 标签类别编号
     */
    @JsonProperty("tag_classification_no")
    private String tagClassificationNo;

    /**
     * 标签类别名称
     */
    @JsonProperty("tag_classification_name")
    private String tagClassificationName;

    /**
     * 开始时间
     */
    @JsonProperty("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonProperty("end_time")
    private Date endTime;

    /**
     * 分页信息
     */
    @JsonProperty("pagination")
    @NotNull(message = "pagination is null")
    private Pagination pagination;


}
