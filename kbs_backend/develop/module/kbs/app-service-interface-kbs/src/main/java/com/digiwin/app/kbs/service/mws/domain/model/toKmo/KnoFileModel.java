package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.digiwin.app.kbs.service.configuration.annotation.NotNull;
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
public class KnoFileModel {

    @JsonProperty(value = "file_id")
    @NotNull(message = "file_id is null")
    private String fileId;
}
