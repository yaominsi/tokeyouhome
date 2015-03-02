package com.fujisan.repository;

import com.fujisan.model.UserModel;
/**
 * 用户信息
 * @author siyaomin
 *
 */
public interface UserRepository  extends BaseRepository<UserModel>{

	UserModel findByTarget(String targetId, String source);

}
