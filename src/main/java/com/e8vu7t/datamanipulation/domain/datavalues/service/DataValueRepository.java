package com.e8vu7t.datamanipulation.domain.datavalues.service;

import java.util.List;
import java.util.Optional;

import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;

/**
 * データ値リポジトリ
 */
public interface DataValueRepository {
    /**
     * データ値を追加する。
     * @param dataValue 追加後のデータ値
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
     * @param dataValue データ値
     * @return 更新後のデータ値
     */
    int update(DataValue dataValue);
}
