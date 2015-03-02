package com.fujisan.api;

import java.io.Serializable;

import com.fujisan.model.LightUpModel;
import com.fujisan.model.OrderModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.model.UserModel;
/**
 * 订单明细
 * @author siyaomin
 *
 */
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	//订单元数据
	private OrderModel order;
	//发起人
	private UserModel fromUser;
	//接收人
	private UserModel toUser;
	//订单对应的活动信息
	private LightUpModel lightUp;
	//订单区域
	private ScopeModel scope;
	public OrderModel getOrder() {
		return order;
	}
	public void setOrder(OrderModel order) {
		this.order = order;
	}
	public UserModel getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserModel fromUser) {
		this.fromUser = fromUser;
	}
	public UserModel getToUser() {
		return toUser;
	}
	public void setToUser(UserModel toUser) {
		this.toUser = toUser;
	}
	public LightUpModel getLightUp() {
		return lightUp;
	}
	public void setLightUp(LightUpModel lightUp) {
		this.lightUp = lightUp;
	}
	public ScopeModel getScope() {
		return scope;
	}
	public void setScope(ScopeModel scope) {
		this.scope = scope;
	}
	
	
}
