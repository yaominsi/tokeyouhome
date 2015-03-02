package com.fujisan.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fujisan.api.OrderDetail;
import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.model.OrderModel;

/**
 * 乘客预约
 * 司机接受订单上限
 * @author siyaomin
 *
 */
public interface OrderService {
	/**
	 * 创建新预约
	 * @param requestContext
	 * @param rideOrderModel
	 * @return
	 */
	public Response<Boolean> create(RequestContext requestContext,OrderModel orderModel);
	/**
	 * 接收预约
	 * @param requestContext
	 * @param rideOrderModel
	 * @return
	 */
	public Response<Boolean> accept(RequestContext requestContext,String id,String note);
	//上车后就finish
	/**
	 * 完成预约，上车后完成预约
	 * @param requestContext
	 * @param rideOrderModel
	 * @return
	 */
	public Response<Boolean> finish(RequestContext requestContext,OrderModel orderModel);
	/**
	 * 司机拒绝客户的预约请求
	 * @param requestContext
	 * @param rideOrderModel
	 * @return
	 */
	public Response<Boolean> reject(RequestContext requestContext,String is,String note);
	/**
	 * 取消
	 * @param requestContext
	 * @param orderModel
	 */
	public Response<Boolean> cancel(RequestContext requestContext,String id,String note);
	/**
	 * 分页查询
	 * @param requestContext
	 * @param model
	 * @param properties
	 * @param direction
	 * @param sortProperties
	 * @param pageable
	 * @return
	 */
	public Page<OrderModel> find(RequestContext requestContext,OrderModel model, List<String> properties, Direction direction, List<String> sortProperties,Pageable pageable);
	/**
	 * 订单明细
	 * @param requestContext
	 * @param id
	 * @return
	 */
	public Response<OrderDetail> detail(RequestContext requestContext, String id);
	
}
