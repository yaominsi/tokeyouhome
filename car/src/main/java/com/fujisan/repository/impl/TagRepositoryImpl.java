package com.fujisan.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.TagModel;
import com.fujisan.repository.TagRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("tagRepository")
public class TagRepositoryImpl extends BaseRepositoryImpl<TagModel> implements
		TagRepository {
	@Autowired
    public TagRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<TagModel, String>getEntityInformation(TagModel.class), mongoOperations);
    }

	public TagRepositoryImpl(MongoEntityInformation<TagModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}

	@Override
	public List<TagModel> getTagsByUserId(String id) {
		Query query=Query.query(Criteria.where("targetId").is(id).and("targetType").is("user"));
		return mongoTemplate.find(query, TagModel.class);
	}
}