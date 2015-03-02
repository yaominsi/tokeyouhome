package com.fujisan.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.ScopeDetail;
import com.fujisan.model.NodeModel;
import com.fujisan.model.ScopeModel;

/**
 * 区域服务
 * @author siyaomin
 *
 */
public interface ScopeService {
	/**
	 * 创建线路
	 * @param requestContext
	 * @param a
	 * @return
	 */
	Response<Boolean> create(RequestContext requestContext,ScopeModel scopeModel);
	/**
	 * 更新线路结点
	 * @param requestContext
	 * @param areaId
	 * @param nodeModels
	 * @return
	 */
	Response<Boolean> updateNode(RequestContext requestContext,String areaId,List<NodeModel> nodeModels);
	/**
	 * 更新线路
	 * @param requestContext
	 * @param areaModel
	 * @return
	 */
	Response<Boolean> update(RequestContext requestContext,ScopeModel areaModel);
		/**
	 * 移除线路
	 * @param requestContext
	 * @param areaId
	 * @return
	 */
	Response<Boolean> delete(RequestContext requestContext,String id);
	/**
	 * 获取区域列表
	 * @param model
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @param pageable
	 * @return
	 */
	Page<ScopeModel> find(RequestContext requestContext,ScopeModel model, List<String> properties, Direction direction, List<String> sortProperties,
			Pageable pageable);
	/**
	 * 区域明细
	 * @param requestContext
	 * @param id
	 * @return
	 */
	Response<ScopeDetail> detail(RequestContext requestContext, String id);
}
