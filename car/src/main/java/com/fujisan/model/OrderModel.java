package com.fujisan.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.common.OrderTypeEnum;
import com.google.common.collect.Lists;

/**
 * 预约信息
 *
 * @author siyaomin
 *
 */
@Document(collection = "orders")
public class OrderModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String fromUserId;
	private String toUserId;
	// 点亮的目标
	private String lightUpId;
	// 点亮的目标的结点
	private String lightUpNodeId;
	// 资源数量
	private Integer seeds;
	// 打分
	private String score;
	// 预约状态信息
	private OrderStatusEnum status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT")
	private Date gmtStatusTime;
	private String operatorId;
	// 留言
	private String note;
	//类型预约还是邀请
	private OrderTypeEnum type;

	@Override
	public List<String> needFieldsForCreate() {
		return Lists.newArrayList("fromUserId", "toUserId", "lightUpId", "seeds", "status",
				"gmtStatusTime", "operatorId");
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getLightUpId() {
		return lightUpId;
	}

	public void setLightUpId(String lightUpId) {
		this.lightUpId = lightUpId;
	}

	public String getLightUpNodeId() {
		return lightUpNodeId;
	}

	public void setLightUpNodeId(String lightUpNodeId) {
		this.lightUpNodeId = lightUpNodeId;
	}

	public Integer getSeeds() {
		return seeds;
	}

	public void setSeeds(Integer seeds) {
		this.seeds = seeds;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public Date getGmtStatusTime() {
		return gmtStatusTime;
	}

	public void setGmtStatusTime(Date gmtStatusTime) {
		this.gmtStatusTime = gmtStatusTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OrderTypeEnum getType() {
		return type;
	}

	public void setType(OrderTypeEnum type) {
		this.type = type;
	}

}