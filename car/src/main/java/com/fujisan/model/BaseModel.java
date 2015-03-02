package com.fujisan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fujisan.common.JsonTimeFormat;
/**
 * 基础模型
 * @author siyaomin
 *
 */
public abstract class BaseModel implements Serializable{

	public static final String final_gmtCreate="gmtCreate";
	public static final String final_gmtModified="gmtModified";
	public static final String final_id="id";
	public static String final_idKey="_id";
	public static String final_isDeleted="isDeleted";
	public static String final_creatorId="creatorId";
	public static String final_lightUpId="lightUpId";
	public static String final_toUserId="toUserId";
	public static String final_status="status";
	
	
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String creator;
	protected String creatorId;
	protected String modifier;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT")
	protected Date gmtCreate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT")
	protected Date gmtModified;
	protected String isDeleted;
	
	//
	protected double[] point;		//操作时的位置
	protected String pointName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 创建时需要的字段 
	 * @return
	 */
	public abstract List<String> needFieldsForCreate();
	
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public double[] getPoint() {
		return point;
	}
	public void setPoint(double[] point) {
		this.point = point;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
}
