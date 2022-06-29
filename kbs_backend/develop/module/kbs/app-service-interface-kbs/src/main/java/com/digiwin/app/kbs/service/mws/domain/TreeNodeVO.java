package com.digiwin.app.kbs.service.mws.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/1/21 10:34
 * @Created yanggld
 * @Description
 */
@Data
public class TreeNodeVO<T> {

    protected Object id;
    protected Object parentId;
    List<T> children = new ArrayList<T>();

    public void add(T node){
        children.add(node);
    }
}