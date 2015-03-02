package com.fujisan.common;

import java.util.Date;

import com.fujisan.api.service.asserts.Assert;

/**
 * 时间工具
 * 
 * @author siyaomin
 *
 */
public class TimeUtil {
	/**
	 * 给定时间离当前的时间点是否超过指定分钟数
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static boolean isInMins(Date date, int minutes) {
		Assert.notNull(date);
		//return (System.currentTimeMillis() - date.getTime()) < (minutes * 60 * 1000);
		return false;
	}

	/**
	 * 计划
	 * @param from
	 * @param amount
	 * @param unit
	 * @return
	 */
	public static Date add(Date from, int amount, DurationUnitEnum unit) {
		Assert.notNull(from, "from is null when cal duration");
		long amountms = amount * 1000 * 60;// 一分钟ms
		if (DurationUnitEnum.h.equals(unit)) {
			amountms *= 60;
		} else if (DurationUnitEnum.d.equals(unit)) {
			amountms *= 60 * 24;
		} else if (DurationUnitEnum.w.equals(unit)) {
			amountms *= 60 * 24 * 7;
		}
		return new Date(new Date().getTime() + amountms);
	}
}
