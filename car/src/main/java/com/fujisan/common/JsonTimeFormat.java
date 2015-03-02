package com.fujisan.common;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 时区转化工具
 * @author siyaomin
 *
 */
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT")
public @interface JsonTimeFormat {

}
