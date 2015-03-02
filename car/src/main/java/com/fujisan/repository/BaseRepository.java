package com.fujisan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fujisan.model.BaseModel;

/**
 * 上层dao，统一ID类型为String
 * 
 * @author siyaomin
 *
 * @param <T>
 */
public interface BaseRepository<T extends BaseModel> {
	/**
	 * 查找最近的一条记录
	 * 
	 * @param t
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @return
	 */
	public T findLast(T t, List<String> properties, Direction direction, List<String> sortProperties);

	/**
	 * 分页查询
	 * 
	 * @param t
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @param pageable
	 * @param clazz
	 * @return
	 */
	public Page<T> findByPage(T t, List<String> properties, Direction direction, List<String> sortProperties,
			Pageable pageable, Class<T> clazz);

	/**
	 * 列表查询
	 * 
	 * @param t
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @param clazz
	 * @return
	 */
	public List<T> findList(T t, List<String> properties, Direction direction, List<String> sortProperties,
			Class<T> clazz);
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public T save(T entity);
	/**
	 * 查询指定的记录
	 * @param id
	 * @param clazz
	 * @return
	 */
	public T findOne(String id, Class<? extends T> clazz);
	/**
	 * 查询指定记录
	 * @param entity
	 * @param properties
	 * @param clazz
	 * @return
	 */
	public T findOne(T entity, List<String> properties, Class<T> clazz);
	/**
	 * 更新字段值
	 * @param id
	 * @param key
	 * @param amount
	 * @param t
	 * @return
	 */
	public T findAndInc(String id, String key, int amount, Class<T> t);
	/**
	 * 统计数量
	 * @param t
	 * @param properties
	 * @return
	 */
	public long count(T t, List<String> properties);
}
