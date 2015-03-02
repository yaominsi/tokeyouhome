package com.fujisan.api;

import java.io.Serializable;

import com.fujisan.model.BaseModel;
import com.fujisan.model.UserModel;
/**
 * 请求vo
 * @author siyaomin
 *
 */
public class RequestContext implements Serializable{

	private static final long serialVersionUID = 1L;
	protected UserModel userModel;
	protected String seq;
	public RequestContext(){}
	//变更与通知
	private BaseModel changeFrom;
	private BaseModel changeTo;
	public RequestContext(UserModel userModel){
		this.userModel=userModel;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public BaseModel getChangeFrom() {
		return changeFrom;
	}
	public void setChangeFrom(BaseModel changeFrom) {
		this.changeFrom = changeFrom;
	}
	public BaseModel getChangeTo() {
		return changeTo;
	}
	public void setChangeTo(BaseModel changeTo) {
		this.changeTo = changeTo;
	}
	
}
