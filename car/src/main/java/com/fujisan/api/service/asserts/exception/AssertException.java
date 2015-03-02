package com.fujisan.api.service.asserts.exception;

import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;

/**
 * 断言异常
 * @author siyaomin
 *
 */
public class AssertException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	/**
	 * 异常类型
	 */
	private BusiTypeEnum scene;
	private OperateTypeEnum operate;
	private Object attach;
	
	public AssertException(BusiTypeEnum scene,OperateTypeEnum operate,String message,Object attach){
		super(message);
		this.setAttach(attach);
		this.scene=scene;
		this.operate=operate;
	}
	public AssertException(String message,Object attach){
		super(message);
		this.setAttach(attach);
	}
	public Object getAttach() {
		return attach;
	}
	public void setAttach(Object attach) {
		this.attach = attach;
	}
	public BusiTypeEnum getScene() {
		return scene;
	}
	public void setScene(BusiTypeEnum scene) {
		this.scene = scene;
	}
	public OperateTypeEnum getOperate() {
		return operate;
	}
	public void setOperate(OperateTypeEnum operate) {
		this.operate = operate;
	}
	
}
