package com.cxy.community.mapper;

import com.cxy.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
//import java.lang.String;

/**
 * @Author 小陈
 * @Date 2020/11/3 22:49
 * @Version 1.0
 * 用户管理
 */
@Mapper
public interface UserMapper {

    /**
     * todo: 搞个登陆拦截
     * 登陆验证
     * @param account 登陆用户
     * @return 用户密码
     */
    String getPassword(String account);

    User getUser(String account);

    /**
     * 新增用户
     * @param user 新增用户对象
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id 要删除用户的ID
     */
    void removeUser(String id);

    /**
     * 修改用户
     * @param user 修改后的用户对象
     */
    void changeUser(User user);

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    List<User> getAllUser();

    void changePassword(@Param("id") String id, @Param("password") String password);
}
