package com.e8vu7t.datamanipulation.domain.datavalues.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.datavalues.model.DataValue;

import lombok.RequiredArgsConstructor;

/**
 * データ値ドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataValueDomainService {
    
    /**
     * データ値リポジトリ
     */
    private final DataValueRepository dataValueRepository;
    
    /**
     * データ値を作成する。
     * @param dataValue データ値
     * @return 作成したデータ値
     */
    public DataValue create(DataValue dataValue){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = dataValueRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        dataValue.setId(newId);
        dataValueRepository.insert(dataValue);
        return dataValue;
    }

    /**
     * データ値を更新する。
     * @param dataValue データ値
     * @return 更新後のデータ値
     */
    public DataValue update(DataValue dataValue){
        dataValueRepository.update(dataValue);
        return dataValue;
    }

    /**
     * データ値一覧を取得する。
     * @return データ値一覧
     */
    public List<DataValue> findAll(){
        return dataValueRepository.findAll();
    }

    /**
     * IDからデータ値一覧を取得する。
     * @param id ID
     * @return データ値一覧
     */
    public List<DataValue> findById(int id){
        return dataValueRepository.findById(id);
    }
}
