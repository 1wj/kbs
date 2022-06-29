package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName RelagationBaseInfoModel 归属知识库信息
 * @Version: v1-知识库迭代一
 **/
@Data
public class RelagationBaseInfoModel {
    /**
     * 归属知识库主键
     */
    @JsonProperty(value = "relegation_base_id")
    private String relegationBaseId;

    /**
     * 归属知识库名称
     */
    @JsonProperty(value = "relegation_base_name")
    private String relegationBaseName;


}
