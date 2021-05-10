package com.cxy.community.mapper;

import com.cxy.community.entity.Admin;
import com.cxy.community.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/3 22:36
 * @Version 1.0
 */
@Mapper
public interface AdminMapper {
// 管理员管理
    /**
     * 新增管理员
     * @param admin 新增的管理员对象
     */
    void addAdmin(Admin admin);

    /**
     * 删除管理员
     * @param id 要删除的管理员ID
     */
    void removeAdmin(String id);

    /**
     * 修改管理员（自己/他人）
     * @param admin 修改后的管理员信息
     */
    void changeAdmin(Admin admin);

    /**
     * 获取所有的管理员信息
     * @return  所有的管理员信息
     */
    List<Admin> getAllAdmin();

// 角色管理
    /**
     * 新增角色
     * @param role 新增的角色对象
     */
    void addRole(Role role);

    /**
     * 删除角色
     * @param id 要删除的角色ID
     */
    void removeRole(String id);

    /**
     * 修改角色
     * @param role 修改后的角色信息
     */
    void changeRole(Role role);

    /**
     * 获取所有的角色信息
     * @return  所有的角色信息
     */
    List<Role> getAllRole();

}
