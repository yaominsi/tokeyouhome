package com.fujisan.web.login;

/**
 * cookie Ê¹ÓÃµÄkey
 * 
 * @author siyaomin
 *
 */
public enum CookieKeyEnum {
	acl_token_key, local_token_key, id, user_id, user_name;
	public String toString() {
		return this.name();
	};
	public static boolean eq(CookieKeyEnum e,String v){
		return e.toString().equals(v);
	}
}
class x{
	public static void main(String[] args) {
		System.out.println(CookieKeyEnum.acl_token_key);
		System.out.println(CookieKeyEnum.eq(CookieKeyEnum.acl_token_key,"acl_token_key"));
	}
}