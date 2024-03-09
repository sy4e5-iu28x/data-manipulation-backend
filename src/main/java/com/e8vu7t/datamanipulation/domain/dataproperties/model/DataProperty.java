package com.e8vu7t.datamanipulation.domain.dataproperties.model;

import lombok.Data;

/**
 * データプロパティ
 */
@Data
public class DataProperty {
    /**
     * プロパティID
     */
    private int id;

    /**
     * プロパティ名
     */
    private String name;

    /**
     * タイプクラスID
     */
    private int typeClassId;
}
