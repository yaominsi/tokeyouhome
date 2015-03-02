package com.fujisan.request;

import java.io.Serializable;

import com.fujisan.model.BaseModel;
/**
 * ∑÷“≥«Î«Û
 * @author siyaomin
 *
 * @param <T>
 */
public class SimplePageRequest<T extends BaseModel> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected T value;
	protected int page;
	protected int size;
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
