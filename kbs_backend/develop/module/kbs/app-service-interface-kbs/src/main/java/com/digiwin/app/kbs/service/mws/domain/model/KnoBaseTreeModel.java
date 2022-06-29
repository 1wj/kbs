package com.digiwin.app.kbs.service.mws.domain.model;

import com.digiwin.app.kbs.service.mws.domain.TreeNodeVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KnoBaseTreeModel extends TreeNodeVO<KnoBaseTreeModel> {
    private String classification_id;
    private String classification_no;
    private String classification_name;
    private String relegation_base_id;
    private String relegation_base_name;
    private String classification_description;

    public KnoBaseTreeModel(Object id, Object parentId) {
        this.setId(id);
        this.setParentId(parentId);
    }
}
