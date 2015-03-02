package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.LightUpModel;
import com.fujisan.repository.LightUpRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("lightUpRepository")
public class LightUpRepositoryImpl extends BaseRepositoryImpl<LightUpModel> implements
		LightUpRepository {
	@Autowired
    public LightUpRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<LightUpModel, String>getEntityInformation(LightUpModel.class), mongoOperations);
    }

	public LightUpRepositoryImpl(MongoEntityInformation<LightUpModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}

	@Autowired
	private MongoTemplate mongoTemplate;
}
