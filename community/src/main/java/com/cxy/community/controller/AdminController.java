package com.cxy.community.controller;

import com.cxy.community.entity.Admin;
import com.cxy.community.entity.Role;
import com.cxy.community.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/9 13:13
 * @Version 1.0
 */

@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

// 管理员管理

    @PostMapping("/addAdmin")
    public void addAdmin(@RequestBody Admin admin){
        adminService.addAdmin(admin);
    }

    @GetMapping("/removeAdmin")
    public void removeAdmin(String id){
        adminService.removeAdmin(id);
    }

    @PostMapping("/removeAdminArr")
    public void removeAdminArr(@RequestBody String[] arr){
        adminService.removeAdminArr(arr);
    }

    @PostMapping("/changeAdmin")
    public void changeAdmin(@RequestBody Admin admin){
        adminService.changeAdmin(admin);
    }

    @GetMapping("/getAllAdmin")
    public List<Admin> getAllAdmin(){
        return adminService.getAllAdmin();
    }

// 角色管理

    @PostMapping("/addRole")
    public void addRole(@RequestBody Role role){
        adminService.addRole(role);
    }

    @GetMapping("/removeRole")
    public void removeRole(String id){
        adminService.removeRole(id);
    }

    @PostMapping("/changeRole")
    public void changeRole(@RequestBody Role role){
        adminService.changeRole(role);
    }

    @GetMapping("/getAllRole")
    public List<Role> getAllRole(){
        return adminService.getAllRole();
    }
}
