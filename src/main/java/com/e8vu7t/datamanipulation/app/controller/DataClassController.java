package com.e8vu7t.datamanipulation.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e8vu7t.datamanipulation.app.service.DataClassService;
import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dataclasses")
public class DataClassController {

    /**
     * データクラスサービス
     */
    private final DataClassService dataClassService;
    
    /**
     * データクラスを作成する
     * @param dataClass データクラス
     * @return 作成後のデータクラス
     */
    @PostMapping()
    public DataClass create(@RequestBody DataClass dataClass){
        return dataClassService.create(dataClass);
    }
    
}
