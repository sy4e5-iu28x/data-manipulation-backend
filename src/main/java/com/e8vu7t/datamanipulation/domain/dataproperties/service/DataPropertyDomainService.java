package com.e8vu7t.datamanipulation.domain.dataproperties.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;

import lombok.RequiredArgsConstructor;

/**
 * データプロパティドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataPropertyDomainService {
    /**
     * データプロパティリポジトリ
     */
    private final DataPropertyRepository dataPropertyRepository;
    
    /**
     * データプロパティを作成する。
     * @param dataProperty データプロパティ
     * @return 作成したデータプロパティ
     */
    public DataProperty create(DataProperty dataProperty){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = dataPropertyRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        dataProperty.setId(newId);
        dataPropertyRepository.insert(dataProperty);
        return dataProperty;
    }

    /**
     * データプロパティを更新する。
     * @param dataProperty データプロパティ
     * @return 更新後のデータプロパティ
     */
    public DataProperty update(DataProperty dataProperty){
        dataPropertyRepository.update(dataProperty);
        return dataProperty;
    }

    /**
     * データプロパティ一覧を取得する。
     * @return データプロパティ一覧
     */
    public List<DataProperty> findAll(){
        return dataPropertyRepository.findAll();
    }
}
