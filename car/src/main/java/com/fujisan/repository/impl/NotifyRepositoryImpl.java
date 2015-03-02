package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.NotifyModel;
import com.fujisan.repository.NotifyRepository;
/**
 * 通知处理中心
 * @author siyaomin
 *
 */
@Repository
public class NotifyRepositoryImpl extends BaseRepositoryImpl<NotifyModel> implements NotifyRepository{

	public NotifyRepositoryImpl(MongoEntityInformation<NotifyModel, String> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}
	@Autowired
    public NotifyRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<NotifyModel, String>getEntityInformation(NotifyModel.class), mongoOperations);
    }
	
}
