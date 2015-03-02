package com.fujisan.web.login;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fujisan.api.Response;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.common.JsonAbout;

/**
 * http 异步请求
 * @author siyaomin
 *
 */
public class AjaxUtil {
	private static final Logger log=Logger.getLogger(AjaxUtil.class);
	/**
	 * 是不是异步
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		
		String requestType = request.getHeader("X-Requested-With");
		if (requestType!=null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param request
	 * @param msg
	 */
	public static void flush(HttpServletResponse response,Response<?> msg){
		Assert.notNull(response);
		Assert.notNull(msg);
		try {
		String res = JsonAbout.asString(msg);
		response.setContentType("application/json");
		PrintWriter writer;
			writer = response.getWriter();
			writer.print(res);
			writer.flush();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
}
