package com.fujisan.notify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fujisan.common.NotifyTypeEnum;
/**
 * 需要发送消息的注解
 * @author siyaomin
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedNotify {
	NotifyTypeEnum value();
}
