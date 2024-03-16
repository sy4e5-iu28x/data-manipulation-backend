package com.e8vu7t.datamanipulation.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e8vu7t.datamanipulation.app.service.DataClassDataPropertyRelationService;
import com.e8vu7t.datamanipulation.domain.relations.model.DataClassDataPropertyRelation;

import lombok.RequiredArgsConstructor;

/**
 * データクラス・データプロパティ関係コントローラー
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/relations/dataclass-dataproperty")
public class DataClassDataPropertyRelationController {
    
    /**
     * データクラス・データプロパティ関係サービス
     */
    private final DataClassDataPropertyRelationService relationService;

    /**
     * データクラス・データプロパティ関係を作成する
     * @param dataProperty データクラス・データプロパティ関係
     * @return 作成後のデータクラス・データプロパティ関係
     */
    @PostMapping()
    public DataClassDataPropertyRelation create(@RequestBody DataClassDataPropertyRelation dataProperty){
        return relationService.create(dataProperty);
    }

    /**
     * データクラス・データプロパティ関係を更新する
     * @param id ID
     * @param dataProperty データクラス・データプロパティ関係
     * @return 更新後のデータクラス・データプロパティ関係
     */
    @PostMapping("/{id}")
    public DataClassDataPropertyRelation update(@PathVariable("id") int id, @RequestBody DataClassDataPropertyRelation dataProperty){
        // 更新対象IDの設定
        dataProperty.setId(id);
        return relationService.update(dataProperty);
    }

    /**
     * データクラスIDをキーにして、データクラス・データプロパティ関係の一覧を取得する。
     * @param dataClassId データクラスID
     * @return データクラス・データプロパティ関係の一覧
     */
    @GetMapping()
    public List<DataClassDataPropertyRelation> findByDataClassId(@RequestParam("dataclassid") Optional<Integer> dataClassId){
        return dataClassId.isPresent() ? relationService.findByDataClassId(dataClassId.get()) : relationService.findAll();
    }
}
