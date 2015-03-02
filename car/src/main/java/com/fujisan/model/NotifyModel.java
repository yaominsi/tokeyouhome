package com.fujisan.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fujisan.common.NotifyStatusEnum;
import com.fujisan.common.NotifyTypeEnum;
import com.google.common.collect.Lists;

/**
 * 通知中心 <br/>
 * 打造一个通知中心， <br/>
 * 处理 订单通知 一般消息通知 推荐通知 留言
 * 
 * @author siyaomin
 *
 */
@Document(collection = "notify")
public class NotifyModel extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String fromUserId;// 通知发起人
	private String toUserId;// 通知接收人
	private String fromUserName;// 名字
	private String toUserName;// 名字
	private String targetId;// 通知涉及对象
	private NotifyTypeEnum type;// 通知类型
	private String level;// 通知紧急级别
	private String content;// 内容
	private NotifyStatusEnum status;// 状态

	public NotifyModel() {
	}

	public NotifyModel(String content) {
		this.content = content;
	}

	@Override
	public List<String> needFieldsForCreate() {
		return Lists.newArrayList("fromUserId", "toUserId");
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

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NotifyTypeEnum getType() {
		return type;
	}

	public void setType(NotifyTypeEnum type) {
		this.type = type;
	}

	public NotifyStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NotifyStatusEnum status) {
		this.status = status;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

}
