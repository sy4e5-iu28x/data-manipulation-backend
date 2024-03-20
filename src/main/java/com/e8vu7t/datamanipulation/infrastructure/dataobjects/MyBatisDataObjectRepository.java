package com.e8vu7t.datamanipulation.infrastructure.dataobjects;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.e8vu7t.datamanipulation.domain.dataobjects.model.DataObject;
import com.e8vu7t.datamanipulation.domain.dataobjects.service.DataObjectRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisDataObjectRepository implements DataObjectRepository {

    /**
     * データオブジェクトマッパー
     */
    private final DataObjectMapper dataPropertyMapper;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataObject dataObject) {
        dataPropertyMapper.insert(dataObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObject> findAll() {
        return dataPropertyMapper.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObject> findById(int id) {
        return dataPropertyMapper.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getMaxId() {
        return dataPropertyMapper.getMaxId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(DataObject dataObject) {
        return dataPropertyMapper.update(dataObject);
    }
}
