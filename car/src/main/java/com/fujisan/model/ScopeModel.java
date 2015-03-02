package com.fujisan.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

/**
 * 加入区域的概念，比较家的位置，在一个小区，有四个门，那么家，这个位置就会有四个结点
 * 
 * 或家附近有两条路，那么就可以在家这个位置添加两个结点
 * 
 * 用于提高成功率
 * 
 * @author siyaomin
 *
 */
@Document(collection = "scopes")
public class ScopeModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<NodeModel> nodes; 
	public ScopeModel(String creatorId,String name,List<NodeModel> nodes){
		this.creatorId=creatorId;
		this.name=name;
		this.nodes=nodes;
	}
	public ScopeModel() {
	}
	@Override
	public List<String> needFieldsForCreate() {
		return Lists.newArrayList(
				"creatorId",
				"name",
				"nodes"
				);
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NodeModel> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeModel> nodes) {
		this.nodes = nodes;
	}

}
