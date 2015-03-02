package com.fujisan.common;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日志序列用于排查问题
 * 目前同一线程共享一个序列
 * @author siyaomin
 *
 */
public class LogSequence {
	public static final String seq(){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");//等价于now.toLocaleString()
        return format.format(new Date());
	}
}
