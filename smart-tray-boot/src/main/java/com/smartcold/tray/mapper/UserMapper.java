package com.smartcold.tray.mapper;

import com.smartcold.tray.entity.comm.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface UserMapper {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
  //  @Select("SELECT `id`,`username`,`type`,`addtime` FROM `user_info` WHERE `username` = #{username} AND `password` = #{password};")
	public UserEntity userLogin(@Param("username") String username,@Param("password") String password);

}
