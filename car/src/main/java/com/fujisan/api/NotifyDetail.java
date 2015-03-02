package com.fujisan.api;

import java.io.Serializable;

import com.fujisan.model.LightUpModel;
import com.fujisan.model.NotifyModel;
import com.fujisan.model.OrderModel;
import com.fujisan.model.UserModel;
/**
 * 订单明细
 * @author siyaomin
 *
 */
public class NotifyDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	//订单元数据
	private NotifyModel notify;
	//关联订单
	private OrderModel order;
	//发起人
	private UserModel fromUser;
	//活动信息
	private LightUpModel lightUp;
	public NotifyModel getNotify() {
		return notify;
	}
	public void setNotify(NotifyModel notify) {
		this.notify = notify;
	}
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
	public LightUpModel getLightUp() {
		return lightUp;
	}
	public void setLightUp(LightUpModel lightUp) {
		this.lightUp = lightUp;
	}
}
