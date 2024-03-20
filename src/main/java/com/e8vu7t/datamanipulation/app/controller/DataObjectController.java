package com.e8vu7t.datamanipulation.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e8vu7t.datamanipulation.app.service.DataObjectService;
import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObjectInformation;

import lombok.RequiredArgsConstructor;


/**
 * データオブジェクトコントローラー
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/dataobjects")
public class DataObjectController {

    /**
     * データオブジェクトサービス
     */
    private final DataObjectService dataClassService;
    
    /**
     * データオブジェクトを作成する
     * @param dataProperty データオブジェクト
     * @return 作成後のデータオブジェクト
     */
    @PostMapping()
    public DataObjectInformation create(@RequestBody DataObjectInformation dataProperty){
        return dataClassService.create(dataProperty);
    }

    /**
     * データオブジェクトの一覧を取得する。
     * @return データオブジェクトの一覧
     */
    @GetMapping()
    public List<DataObjectInformation> findAll(){
        return dataClassService.findAll();
    }
}
