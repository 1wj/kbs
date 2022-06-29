package com.digiwin.app.kbs.service.mws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName TagModel
 * @Version: v1-知识库迭代一
 **/
@Data
public class TagModel {
    @JsonProperty("tag_id")
    private String objectId;

    /**
     * 标签名称
     */
    @JsonProperty(value = "tag_name")
    private String tagName;

    /**
     * 类别描述
     */
    @JsonProperty(value = "tag_description")
    private String tagDescription;


    /**
     * 标签类别信息
     */
    @JsonProperty(value = "tag_classification_info")
    private List<TagClassificationInfoModel> tagClassifications;

    /**
     * 是否有效 Y.生效;V.失效
     */
    @JsonProperty(value = "manage_status")
    private String manageStatus;

    /**
     * "1.轉移;2.複製並轉移
     * 為1，將當前標籤對應的標籤類別替換為入參對應標籤類別
     * 為2，保留當前標籤對應的類別，並加入入參對應標籤類別"
     */
    @JsonProperty(value = "transfer_flag")
    private String transferFlag;

}
