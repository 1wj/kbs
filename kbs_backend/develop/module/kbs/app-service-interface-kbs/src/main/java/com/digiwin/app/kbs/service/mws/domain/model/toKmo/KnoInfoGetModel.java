package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.digiwin.app.kbs.service.configuration.annotation.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName KnowledgeInfoModel
 * @Description 获取知识清单入参
 * @Author HeX
 * @Date 2022/2/23 13:56
 * @Version 1.0
 **/
@Data
public class KnoInfoGetModel {
    /**
     * 知识分类id
     */
    @JsonProperty("classification_id")
    private String classificationId;


    /**
     * 知识库编号
     */
    @JsonProperty("base_no")
    private String baseNo;

    /**
     * 搜索内容
     */
    @JsonProperty("search_content")
    private String searchContent;

    /**
     * 开始时间
     */
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @JsonProperty("end_time")
    private String endTime;

    /**
     * 是否生效
     */
    @JsonProperty("manage_status")
    private String manageStatus;

    /**
     * 分页信息
     */
    @JsonProperty("pagination")
    private Pagination pagination;
}
