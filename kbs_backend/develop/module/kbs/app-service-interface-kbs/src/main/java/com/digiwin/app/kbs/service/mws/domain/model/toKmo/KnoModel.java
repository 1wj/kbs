package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.digiwin.app.kbs.service.configuration.annotation.NotNull;
import com.digiwin.app.kbs.service.mws.domain.model.TagModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName KnoModel
 * @Description 用于获取知识详情
 * @Author HeX
 * @Date 2022/2/23 14:56
 * @Version 1.0
 **/
@Data
public class KnoModel {

    /**
     * 知识主键
     */
    @JsonProperty("knowledge_id")
    @NotNull(message = "knowledge_id is null")
    private String knowledgeId;

    /**
     * 转移标识-用于知识复制转移
     */
    @JsonProperty(value = "transfer_flag")
    private String transferFlag;


    @JsonProperty(value = "knowledge_classification_info")
    private List<KnoClassificationModel> classificationModelList;

    @JsonProperty(value = "tag_info")
    private List<KnoTagModel> tagModels;


}
