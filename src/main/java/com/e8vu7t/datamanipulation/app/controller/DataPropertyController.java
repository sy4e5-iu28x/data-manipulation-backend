package com.e8vu7t.datamanipulation.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e8vu7t.datamanipulation.app.service.DataPropertyService;
import com.e8vu7t.datamanipulation.domain.dataproperties.model.DataProperty;

import lombok.RequiredArgsConstructor;


/**
 * データプロパティコントローラー
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/dataproperties")
public class DataPropertyController {

    /**
     * データプロパティサービス
     */
    private final DataPropertyService dataClassService;
    
    /**
     * データプロパティを作成する
     * @param dataProperty データプロパティ
     * @return 作成後のデータプロパティ
     */
    @PostMapping()
    public DataProperty create(@RequestBody DataProperty dataProperty){
        return dataClassService.create(dataProperty);
    }

    /**
     * データプロパティを更新する
     * @param id ID
     * @param dataProperty データプロパティ
     * @return 更新後のデータプロパティ
     */
    @PostMapping("/{id}")
    public DataProperty update(@PathVariable("id") int id, @RequestBody DataProperty dataProperty){
        // 更新対象IDの設定
        dataProperty.setId(id);
        return dataClassService.update(dataProperty);
    }

    /**
     * データプロパティの一覧を取得する。
     * @return データプロパティの一覧
     */
    @GetMapping()
    public List<DataProperty> findAll(){
        return dataClassService.findAll();
    }
}
