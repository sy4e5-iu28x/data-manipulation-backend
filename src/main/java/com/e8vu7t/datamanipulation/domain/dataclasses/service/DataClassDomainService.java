package com.e8vu7t.datamanipulation.domain.dataclasses.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;

import lombok.RequiredArgsConstructor;

/**
 * データクラスドメインサービス
 */
@Service
@RequiredArgsConstructor
public class DataClassDomainService {
    
    /**
     * データクラスリポジトリ
     */
    private final DataClassRepository dataClassRepository;
    
    /**
     * データクラスを作成する。
     * @param dataClass データクラス
     * @return 作成したデータクラス
     */
    public DataClass create(DataClass dataClass){
        // 登録済み最大IDを取得
        Optional<Integer> currentMaxId = dataClassRepository.getMaxId();
        
        var newId = currentMaxId.orElse(0) + 1;
        dataClass.setId(newId);
        dataClassRepository.insert(dataClass);
        return dataClass;
    }
}
