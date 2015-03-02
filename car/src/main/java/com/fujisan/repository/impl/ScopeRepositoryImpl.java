package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.ScopeModel;
import com.fujisan.repository.ScopeRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("scopeRepository")
public class ScopeRepositoryImpl extends BaseRepositoryImpl<ScopeModel> implements
		ScopeRepository {
	@Autowired
    public ScopeRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<ScopeModel, String>getEntityInformation(ScopeModel.class), mongoOperations);
    }

	public ScopeRepositoryImpl(MongoEntityInformation<ScopeModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}
}