package com.e8vu7t.datamanipulation.domain.dataobjects.model;

import lombok.Data;

/**
 * データオブジェクト
 */
@Data
public class DataObject {
    
    /**
     * ID
     */
    private int id;

    /**
     * 準拠データクラスID
     */
    private int dataclassId;
}
