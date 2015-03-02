package com.fujisan.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.fujisan.model.OrderModel;
import com.fujisan.repository.OrderRepository;

/**
 * 服务提供者
 * 
 * @author siyaomin
 *
 */
@Repository("orderRepository")
public class OrderRepositoryImpl extends BaseRepositoryImpl<OrderModel> implements
		OrderRepository {
	@Autowired
    public OrderRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<OrderModel, String>getEntityInformation(OrderModel.class), mongoOperations);
    }

	public OrderRepositoryImpl(MongoEntityInformation<OrderModel, String> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}
}