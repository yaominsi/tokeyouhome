package com.fujisan.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

/**
 * 结点信息
 * 
id	long
point	
tile_code	
creator_type	varchar
is_light_up	char
order	int
name	varchar
path_id	long
last_light	
 * @author siyaomin
 *
 */
@Document(collection="nodes")
public class NodeModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String scopeId;
	private String name;
	private Integer order;
	public NodeModel(){}
	public NodeModel(double[] point,String name){
		this.point=point;
		this.name=name;
	}
	public NodeModel(String scopeId){
		this.scopeId=scopeId;
	}
	public NodeModel(String scopeId,double[] point,String name){
		this.scopeId=scopeId;
		this.point=point;
		this.name=name;
	}
	@Override
	public
	List<String> needFieldsForCreate() {
		
		return Lists.newArrayList(
				"point",
				"userId",
				"creatorId"
				);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}
	
}
