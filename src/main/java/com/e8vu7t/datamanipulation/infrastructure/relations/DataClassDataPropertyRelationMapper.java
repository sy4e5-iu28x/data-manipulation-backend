package com.e8vu7t.datamanipulation.infrastructure.relations;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.e8vu7t.datamanipulation.domain.relations.model.DataClassDataPropertyRelation;

/**
 * データクラス・データプロパティ関係マッパー
 */
@Mapper
public interface DataClassDataPropertyRelationMapper {
    /**
     * データクラス・データプロパティ関係を追加する。
     * @param classPropertyRelation データクラス・データプロパティ関係
     */
    void insert(DataClassDataPropertyRelation classPropertyRelation);

    /**
     * データクラス・データプロパティ関係の一覧を取得する。
     * @return データクラス・データプロパティ関係一覧
     */
    List<DataClassDataPropertyRelation> findAll();

    /**
     * データクラス・データプロパティ関係の最大IDを取得する。
     * @return データクラス・データプロパティ関係IDでの最大ID
     */
    Optional<Integer> getMaxId();

    /**
     * データクラス・データプロパティ関係を更新する。
     * @param classPropertyRelation
     * @return 更新後のデータクラス・データプロパティ関係
     */
    int update(DataClassDataPropertyRelation classPropertyRelation);
}
