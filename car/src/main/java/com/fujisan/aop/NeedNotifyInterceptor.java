package com.fujisan.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.fujisan.api.RequestContext;
import com.fujisan.common.NotifyTypeEnum;
import com.fujisan.model.OrderModel;
import com.fujisan.notify.ModelChangeDispose;
import com.fujisan.notify.NeedNotify;

/**
 * ¥¶¿Ì
 * 
 * @author siyaomin
 *
 */
public class NeedNotifyInterceptor {
	private static final Logger log = Logger.getLogger(NeedNotifyInterceptor.class);
	@Autowired
	private ModelChangeDispose<OrderModel> orderChangeDispose;
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		log.info("around start");
		Object result = pjp.proceed();
		try {
			MethodSignature ms = (MethodSignature) pjp.getSignature();
			log.info(ms.getDeclaringTypeName());
			NeedNotify needNotify=ms.getMethod().getAnnotation(NeedNotify.class);
			//
			RequestContext requestContext=null;
			Object[] args = pjp.getArgs();
			if (args != null && args.length > 0) {
				for (Object arg : args) {
					if (arg instanceof RequestContext) {
						requestContext=(RequestContext) arg;
						break;
					}
				}
			}
			if (needNotify!=null) {
				if(needNotify.value().equals(NotifyTypeEnum.order)){
					orderChangeDispose.dispose(requestContext, args);
				}
			}
			
			log.info("around end");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return result;
	}
	public void setOrderChangeDispose(ModelChangeDispose<OrderModel> orderChangeDispose) {
		this.orderChangeDispose = orderChangeDispose;
	}

}
