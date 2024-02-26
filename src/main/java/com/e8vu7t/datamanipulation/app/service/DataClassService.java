package com.e8vu7t.datamanipulation.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;
import com.e8vu7t.datamanipulation.domain.dataclasses.service.DataClassDomainService;

import lombok.RequiredArgsConstructor;

/**
 * データクラスサービス
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DataClassService {
    
    /**
     * データクラスドメインサービス
     */
    private final DataClassDomainService dataClassDomainService;

    /**
     * データクラスを作成する。
     * @param dataClass データクラス
     * @return 作成したデータクラス
     */
    public DataClass create(DataClass dataClass){
        return dataClassDomainService.create(dataClass);
    }

}
