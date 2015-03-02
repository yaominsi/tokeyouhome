package com.fujisan.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 乘客预约单的状态
 * @author siyaomin
 *
 */
public enum OrderStatusEnum {
	created, 	//创建
	read, 		//已读
	accept, 	//接受
	rejected, 	//拒绝
	gotOn, 		//已上车
	cancel, 	//取消
	finish;		//乘客完结并打分
	public static Map<OrderStatusEnum,String> getMap(){
		Map<OrderStatusEnum,String> map=new HashMap<OrderStatusEnum, String>();
		map.put(created, "创建");
		map.put(read, "已读");
		map.put(accept, "接受");
		map.put(rejected, "拒绝");
		map.put(gotOn, "处理中..");
		map.put(cancel, "取消");
		map.put(finish, "完结");
		return Collections.unmodifiableMap(map);
	}
}
