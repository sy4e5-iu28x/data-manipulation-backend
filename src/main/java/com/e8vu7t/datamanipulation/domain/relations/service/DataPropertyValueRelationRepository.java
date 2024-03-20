package com.e8vu7t.datamanipulation.domain.relations.service;

import java.util.List;
import java.util.Optional;

import com.e8vu7t.datamanipulation.domain.relations.model.DataPropertyValueRelation;

/**
 * データプロパティ・値関係リポジトリ
 */
public interface DataPropertyValueRelationRepository {
    /**
     * データプロパティ・値関係を追加する。
     * @param propertyValueRelation 追加後のデータプロパティ・値関係
     */
    void insert(DataPropertyValueRelation propertyValueRelation);

    /**
     * データプロパティ・値関係の一覧を取得する。
     * @return データプロパティ・値関係一覧
     */
    List<DataPropertyValueRelation> findAll();

    /**
     * データプロパティ・値関係のIDから取得する。
     * @param param データプロパティ・値関係
     * @return データプロパティ・値関係
     */
    List<DataPropertyValueRelation> findByPropertyValue(DataPropertyValueRelation param);

    /**
     * IDからデータプロパティ・値関係のIDから取得する。
     * @param id ID
     * @return データプロパティ・値関係
     */
    List<DataPropertyValueRelation> findById(int id);

    /**
     * データプロパティ・値関係の最大IDを取得する。
     * @return データプロパティ・値関係IDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データプロパティ・値関係を更新する。
     * @param propertyValueRelation データプロパティ・値関係
     * @return 更新後のデータプロパティ・値関係
     */
    int update(DataPropertyValueRelation propertyValueRelation);
}
