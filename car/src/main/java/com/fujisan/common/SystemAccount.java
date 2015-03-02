package com.fujisan.common;


/**
 * 系统的一些帐号，通过这些帐号可以发通知。
 * @author siyaomin
 *
 */
//TODO 可以配置的bean
public class SystemAccount {
	private String notify;

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	} 
}
