package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.UserModel;
import com.fujisan.repository.UserRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("userRepository")
public class UserRepositoryImpl extends BaseRepositoryImpl<UserModel> implements
		UserRepository {
	
	@Autowired
    public UserRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<UserModel, String>getEntityInformation(UserModel.class), mongoOperations);
    }

	public UserRepositoryImpl(MongoEntityInformation<UserModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}

	@Override
	public UserModel findByTarget(String targetId, String source) {
		Query query=Query.query(Criteria.where("targetId").is(targetId).and("source").is(source));
		return mongoTemplate.findOne(query, UserModel.class);
	}
}