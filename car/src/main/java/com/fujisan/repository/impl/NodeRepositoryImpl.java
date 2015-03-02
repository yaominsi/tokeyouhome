package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.NodeModel;
import com.fujisan.repository.NodeRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("nodeRepository")
public class NodeRepositoryImpl extends BaseRepositoryImpl<NodeModel> implements
		NodeRepository {
	@Autowired
    public NodeRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<NodeModel, String>getEntityInformation(NodeModel.class), mongoOperations);
    }

	public NodeRepositoryImpl(MongoEntityInformation<NodeModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}
}