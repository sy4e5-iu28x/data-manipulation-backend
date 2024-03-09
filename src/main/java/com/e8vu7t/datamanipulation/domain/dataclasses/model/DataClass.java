package com.e8vu7t.datamanipulation.domain.dataclasses.model;

import lombok.Data;

/**
 * データクラス
 */
@Data
public class DataClass {
    
    /**
     * データクラスID
     */
    private int id;

    /**
     * データクラス名
     */
    private String name;

    /**
     * データクラスタイプ
     */
    private String type;
}
