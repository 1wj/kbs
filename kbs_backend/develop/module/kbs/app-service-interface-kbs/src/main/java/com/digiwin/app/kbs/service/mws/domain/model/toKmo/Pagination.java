package com.digiwin.app.kbs.service.mws.domain.model.toKmo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName pagination
 * @Description TODO
 * @Author HeX
 * @Date 2022/2/23 11:18
 * @Version 1.0
 **/
@Data
public class Pagination {

    /**
     * 页签索引
     */
    @JsonProperty(value = "page")
    private String page;

    /**
     * 页签大小
     */
    @JsonProperty(value = "page_size")
    private String pageSize;

}
