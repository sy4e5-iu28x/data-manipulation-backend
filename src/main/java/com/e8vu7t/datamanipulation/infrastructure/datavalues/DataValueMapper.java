package com.e8vu7t.datamanipulation.infrastructure.datavalues;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;

/**
 * データ値マッパー
 */
@Mapper
public interface DataValueMapper {
    /**
     * データ値を追加する。
     * @param dataValue データ値
     */
    void insert(DataValue dataValue);

    /**
     * データ値の一覧を取得する。
     * @return データ値一覧
     */
    List<DataValue> findAll();

    /**
     * データ値の最大IDを取得する。
     * @return データ値IDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データ値を更新する。
     * @param dataValue
     * @return 更新後のデータ値
     */
    int update(DataValue dataValue);
}
