package com.fujisan.api;

import java.io.Serializable;

import com.fujisan.common.ResponseCodeEnum;
/**
 * œÏ”¶
 * @author siyaomin
 *
 * @param <T>
 */
public class Response<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected ResponseCodeEnum code;
	boolean isSuccess;
	String desc;
	protected T value;
	
	public Response(){this.isSuccess=true;}
	public Response(boolean isSuccess,String desc){
		this.isSuccess=isSuccess;
		this.desc=desc;
	}
	public Response(boolean isSuccess,ResponseCodeEnum code,String desc){
		this.isSuccess=isSuccess;
		this.desc=desc;
		this.code=code;
	}
	public Response(boolean isSuccess,T value,String desc){
		this.isSuccess=isSuccess;
		this.desc=desc;
		this.value=value;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
}
