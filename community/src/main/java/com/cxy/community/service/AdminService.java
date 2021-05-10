package com.cxy.community.service;

import com.cxy.community.entity.Admin;
import com.cxy.community.entity.Role;
import com.cxy.community.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/4 3:44
 * @Version 1.0
 */
@Service
public class AdminService {

    @Autowired(required = false)
    private AdminMapper adminMapper;

    public void addAdmin(Admin admin){
        adminMapper.addAdmin(admin);
    }

    public void removeAdmin(String id){
        adminMapper.removeAdmin(id);
    }

    public void removeAdminArr(String[] arr){
        for (String s : arr) {
            removeAdmin(s);
        }
    }

    public void changeAdmin(Admin admin){
        adminMapper.changeAdmin(admin);
    }

    public List<Admin> getAllAdmin(){
        return adminMapper.getAllAdmin();
    }

// 角色管理

    public void addRole(Role role){
        adminMapper.addRole(role);
    }

    public void removeRole(String id){
        adminMapper.removeRole(id);
    }

    public void changeRole(Role role){
        adminMapper.changeRole(role);
    }

    public List<Role> getAllRole(){
        return adminMapper.getAllRole();
    }
}
