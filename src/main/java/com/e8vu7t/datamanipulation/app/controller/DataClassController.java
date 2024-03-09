package com.e8vu7t.datamanipulation.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e8vu7t.datamanipulation.app.service.DataClassService;
import com.e8vu7t.datamanipulation.domain.dataclasses.model.DataClass;

import lombok.RequiredArgsConstructor;

/**
 * データクラスコントローラ
 */
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

    /**
     * データクラスを更新する
     * @param id ID
     * @param dataClass データクラス
     * @return 更新後のデータクラス
     */
    @PostMapping("/{id}")
    public DataClass update(@PathVariable("id") int id, @RequestBody DataClass dataClass){
        // 更新対象IDの設定
        dataClass.setId(id);
        return dataClassService.update(dataClass);
    }

    /**
     * データクラスの一覧を取得する。
     * @return データクラスの一覧
     */
    @GetMapping()
    public List<DataClass> findAll(){
        return dataClassService.findAll();
    }
}
