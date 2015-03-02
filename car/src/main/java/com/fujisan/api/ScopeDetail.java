package com.fujisan.api;

import java.io.Serializable;
import java.util.List;

import com.fujisan.model.NodeModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.model.UserModel;
/**
 * 区域明细
 * @author siyaomin
 *
 */
public class ScopeDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	//基础信息
	private ScopeModel scope;
	//创建人
	private UserModel fromUser;
	//结点
	private List<NodeModel> nodes;
	public ScopeModel getScope() {
		return scope;
	}
	public void setScope(ScopeModel scope) {
		this.scope = scope;
	}
	public UserModel getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserModel fromUser) {
		this.fromUser = fromUser;
	}
	public List<NodeModel> getNodes() {
		return nodes;
	}
	public void setNodes(List<NodeModel> nodes) {
		this.nodes = nodes;
	}
	
}
