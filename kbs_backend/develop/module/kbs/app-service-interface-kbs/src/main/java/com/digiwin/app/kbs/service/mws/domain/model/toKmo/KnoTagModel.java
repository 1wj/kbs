package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName KnoTagModel
 * @Description 知识标签
 * @Author HeX
 * @Date 2022/2/23 16:40
 * @Version 1.0
 **/
@Data
public class KnoTagModel {

    @JsonProperty(value = "tag_id")
    private String tagId;
}
