package com.e8vu7t.datamanipulation.infrastructure.dataproperties;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;

/**
 * データプロパティマッパー
 */
@Mapper
public interface DataPropertyMapper {
    /**
     * データプロパティを追加する。
     * @param dataProperty データプロパティ
     */
    void insert(DataProperty dataProperty);

    /**
     * データプロパティの一覧を取得する。
     * @return データプロパティ一覧
     */
    List<DataProperty> findAll();

    /**
     * データプロパティの最大IDを取得する。
     * @return データプロパティIDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データプロパティを更新する。
     * @param dataProperty
     * @return 更新後のデータプロパティ
     */
    int update(DataProperty dataProperty);
}
