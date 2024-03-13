package com.e8vu7t.datamanipulation.domain.relations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.relations.model.DataClassDataPropertyRelation;

import lombok.RequiredArgsConstructor;

/**
 * データクラス・データプロパティ関係ドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataClassDataPropertyRelationDomainService {
    
    /**
     * データクラス・データプロパティ関係リポジトリ
     */
    private final DataClassDataPropertyRelationRepository classPropertyRelationRepository;
    
    /**
     * データクラス・データプロパティ関係を作成する。
     * @param classPropertyRelation データクラス・データプロパティ関係
     * @return 作成したデータクラス・データプロパティ関係
     */
    public DataClassDataPropertyRelation create(DataClassDataPropertyRelation classPropertyRelation){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = classPropertyRelationRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        classPropertyRelation.setId(newId);
        classPropertyRelationRepository.insert(classPropertyRelation);
        return classPropertyRelation;
    }

    /**
     * データクラス・データプロパティ関係を更新する。
     * @param classPropertyRelation データクラス・データプロパティ関係
     * @return 更新後のデータクラス・データプロパティ関係
     */
    public DataClassDataPropertyRelation update(DataClassDataPropertyRelation classPropertyRelation){
        classPropertyRelationRepository.update(classPropertyRelation);
        return classPropertyRelation;
    }

    /**
     * データクラス・データプロパティ関係一覧を取得する。
     * @return データクラス・データプロパティ関係一覧
     */
    public List<DataClassDataPropertyRelation> findAll(){
        return classPropertyRelationRepository.findAll();
    }
}
