package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName KnoClassificationModel
 * @Description 知识分类model
 * @Author HeX
 * @Date 2022/2/23 16:39
 * @Version 1.0
 **/
@Data
public class KnoClassificationModel {

    @JsonProperty(value = "knowledge_classification_id")
    private String knowledgeClassificationId;
}
