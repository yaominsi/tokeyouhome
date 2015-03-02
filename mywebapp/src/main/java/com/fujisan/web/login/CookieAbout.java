package com.fujisan.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.UserService;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.model.UserModel;

/**
 * cookie相关的
 * @author siyaomin
 *
 */
@Component
public class CookieAbout {
@Autowired
private UserService userService;
	private static final Logger log=Logger.getLogger(CookieAbout.class);
	public static final int max_age=60*10;//十分钟
	/**
	 * 未登录return false
	 * 
	 * @param cookies
	 * @param requestContext
	 * @return
	 */
	public boolean setupRequestContext(Cookie[] cookies, final RequestContext requestContext) {
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (CookieKeyEnum.eq(CookieKeyEnum.acl_token_key,cookie.getName())) {
					String cv = cookie.getValue();
					if (StringUtils.isBlank(cv)) {
						//TODO
					}else{
						//setup RequestContext
					}
					break;
				}else if(CookieKeyEnum.eq(CookieKeyEnum.local_token_key,cookie.getName())){
					//setup
					String cv = cookie.getValue();
					if (StringUtils.isBlank(cv)) {
						return false;
					}else{
						//setup RequestContext
						Response<UserModel> user = userService.getUserByUserId(requestContext, cv);
						if(user.getValue()!=null){
							RequestContextManager.init(user.getValue());
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * 登录或注册之后
	 * @param response
	 * @param userModel
	 * @return
	 */
	public boolean flushCookies(HttpServletResponse response, UserModel userModel){
		Assert.notNull(userModel);
		Cookie cookie=new Cookie(CookieKeyEnum.local_token_key.toString(), userModel.getId());
		cookie.setPath("/");
		cookie.setMaxAge(max_age);
		response.addCookie(cookie);
		try {
			response.flushBuffer();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return true;
	}
}
