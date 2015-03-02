package com.fujisan.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

/**
 * 用户
 * 
 * @author siyaomin
 *
 */
@Document(collection="users")
public class UserModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String targetId;
	private String source;//哪个系统
	private String name;
	private String nickName;
	private String plateNo;
	private String signature;
	private String allowInvited;
	private String mobile;
	private String passwd;
	private List<TagModel> tags;
	public UserModel(){}
	public UserModel(String userId,String userName){
		this.id=userId;
		this.name=userName;
	}
	
	@Override
	public
	List<String> needFieldsForCreate() {
		return Lists.newArrayList(
				"targetId",
				"source",
				"name",
				"nickName"
				);
	}
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAllowInvited() {
		return allowInvited;
	}
	
	public void setAllowInvited(String allowInvited) {
		this.allowInvited = allowInvited;
	}

	public List<TagModel> getTags() {
		return tags;
	}

	public void setTags(List<TagModel> tags) {
		this.tags = tags;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


}
