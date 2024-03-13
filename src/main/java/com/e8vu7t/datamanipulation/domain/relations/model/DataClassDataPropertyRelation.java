package com.e8vu7t.datamanipulation.domain.relations.model;

import lombok.Data;

/**
 * データクラス・データプロパティ関係
 */
@Data
public class DataClassDataPropertyRelation {
    
    /**
     * ID
     */
    private int id;

    /**
     * データクラスID
     */
    private int dataclassId;

    /**
     * データプロパティID
     */
    private int datapropertyId;
}
