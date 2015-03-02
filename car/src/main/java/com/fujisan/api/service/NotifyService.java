package com.fujisan.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fujisan.api.NotifyDetail;
import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.common.NotifyStatusEnum;
import com.fujisan.model.NotifyModel;

/**
 * 通知中心服务处理各种通知的发送与处理
 * 
 * @author siyaomin
 *
 */
public interface NotifyService {
	/**
	 * 创建
	 * 
	 * @param notifyModel
	 * @return
	 */
	public abstract Response<Boolean> create(RequestContext requestContext, NotifyModel notifyModel);

	/**
	 * 标记为已处理
	 * 
	 * @param notifyModel
	 * @return
	 */
	public abstract Response<Boolean> read(RequestContext requestContext, String id);

	/**
	 * 添加到TODO
	 * 
	 * @param requestContext
	 * @param Id
	 * @return
	 */
	public abstract Response<Boolean> addToDo(RequestContext requestContext, String Id);
	/**
	 * 删除
	 * @param requestContext
	 * @param Id
	 * @return
	 */
	public abstract Response<Boolean> finish(RequestContext requestContext, String Id);
	/**
	 * 查询
	 * @param requestContext
	 * @param model
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @param pageable
	 * @return
	 */
	public Page<NotifyModel> find(RequestContext requestContext,NotifyModel model, List<String> properties, Direction direction, List<String> sortProperties,Pageable pageable);
	/**
	 * 明细信息
	 * @param current
	 * @param id
	 * @return
	 */
	public abstract Response<NotifyDetail> detail(RequestContext current, String id);
	/**
	 * 数量统计
	 * @param current
	 * @param toUserId
	 * @param status
	 * @return
	 */
	public abstract Response<Map<NotifyStatusEnum, Long>> counts(RequestContext current, String toUserId,List<NotifyStatusEnum> status);
}
