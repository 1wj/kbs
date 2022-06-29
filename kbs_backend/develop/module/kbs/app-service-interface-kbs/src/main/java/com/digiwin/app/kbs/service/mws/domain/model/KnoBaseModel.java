package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName KnoBaseModel
 * @Description 知识库model
 * @Version: v1-知识库迭代一
 **/
@Data
public class KnoBaseModel {
    @JsonProperty("base_id")
    private String objectId;
    /**
     * 知识库编号
     */
    @JsonProperty("base_no")
    private String baseNo;
    /**
     * 知识库名
     */
    @JsonProperty("base_name")
    private String baseName;
    /**
     * 知识库描述
     */
    @JsonProperty("base_description")
    private String baseDescription;
}
