package com.fujisan.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;
/**
 * ±Í«©±Ì
 * @author siyaomin
 *
 */
@Document(collection="tags")
public class TagModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String targetId;
	private String targetType;//user,topic
	private String content;
	public TagModel(String targetId,String targetType,String content){
		this.targetId=targetId;
		this.targetType=targetType;
		this.content=content;
	}
	@Override
	public
	List<String> needFieldsForCreate() {
		return Lists.newArrayList(
				"targetId",
				"targetType",
				"content"
				);
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
