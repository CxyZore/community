package com.cxy.community.controller;

import com.cxy.community.entity.Content;
import com.cxy.community.entity.User;
import com.cxy.community.service.ContentService;
import com.cxy.community.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/4 17:22
 * @Version 1.0
 */
@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Boolean login (String account,String pd){
        return userService.login(account,pd);
    }

    @GetMapping("/getUser")
    public User getUser(String account){
        return userService.getUser(account);
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/removeUser")
    public void removeUser(String id){
        userService.removeUser(id);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PostMapping("/changeUser")
    public void changeUser(@RequestBody User user){
        userService.changeUser(user);
    }

//    @PostMapping("/addHomework")
//    public void addHomework(@RequestBody Homework homework) {
//        homeworkService.addHomework(homework);
//    }
}
