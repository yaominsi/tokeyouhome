package com.fujisan.repository.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.fujisan.api.service.asserts.Assert;
import com.fujisan.model.BaseModel;
import com.fujisan.repository.BaseRepository;
import com.fujisan.repository.DaoUtil;

/**
 * 上层dao，统一ID类型为String
 * 
 * @author siyaomin
 *
 * @param <T>
 */
public class BaseRepositoryImpl<T extends BaseModel> extends SimpleMongoRepository<T, String> implements
		BaseRepository<T> {

	public BaseRepositoryImpl(MongoEntityInformation<T, String> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}

	@Autowired
	protected MongoTemplate mongoTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public T findLast(T t, List<String> properties, Direction direction, List<String> sortProperties) {
		Query query = DaoUtil.genQuery(t, properties);
		Sort sort = new Sort(direction, sortProperties);
		query.with(sort);
		return (T) mongoTemplate.findOne(query, t.getClass());
	}

	@Override
	public Page<T> findByPage(T t, List<String> properties, Direction direction, List<String> sortProperties,
			Pageable pageable ,Class<T> clazz) {
		Query query = DaoUtil.genQuery(t, properties);
		query.with(pageable);
		query.with(new Sort(direction, sortProperties));
 		long count=mongoTemplate.count(DaoUtil.genQuery(t, properties), t.getClass());
		List<T> list=null;
		if (count>0) {
			list=mongoTemplate.find(query, clazz);
		}else{
			list=Collections.emptyList();
		}
		return new PageImpl<T>(list, pageable, count);
	}
	@Override
	public T save(T entity) {
		if (entity.getId() != null) {
			Update update=DaoUtil.genUpdate(entity);
			Query query = Query.query(Criteria.where(BaseModel.final_idKey).is(entity.getId()));
			mongoTemplate.updateFirst(query, update, entity.getClass());
		} else {
			return super.save(entity);
		}
		return entity;
	}
	@Override
	public T findOne(String id,Class<? extends T> clazz) {
		Assert.notNull(id);
		Query query=Query.query(Criteria.where(BaseModel.final_idKey).is(id));
		return mongoTemplate.findOne(query,clazz);
	}

	@Override
	public T findOne(T entity,List<String> properties,Class<T> clazz) {
		Assert.notNull(entity);
		return mongoTemplate.findOne(DaoUtil.genQuery(entity, properties),clazz);
	}

	@Override
	public List<T> findList(T t, List<String> properties, Direction direction, List<String> sortProperties,
			Class<T> clazz) {
		Query query = DaoUtil.genQuery(t, properties);
		Sort sort = new Sort(direction, sortProperties);
		query.with(sort);
		return mongoTemplate.find(query, clazz);
	}

	@Override
	public T findAndInc(String id, String key, int amount, Class<T> t) {
		Query query = Query.query(Criteria.where(BaseModel.final_idKey).is(id));
		return mongoTemplate.findAndModify(query,new Update().inc(key, amount), t);
	}

	@Override
	public long count(T t, List<String> properties) {
		Assert.notNull(t, "count操作参数不能为空");
		return mongoTemplate.count(DaoUtil.genQuery(t, properties), t.getClass());
	}
}
