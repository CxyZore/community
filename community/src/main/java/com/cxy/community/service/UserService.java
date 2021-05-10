package com.cxy.community.service;

import com.cxy.community.entity.User;
import com.cxy.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/4 4:09
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public Boolean login(String account, String pd){
        return pd.equals(userMapper.getPassword(account));
    }

    public User getUser(String account) {
        return userMapper.getUser(account);
    }

    public void addUser(User user){
        userMapper.addUser(user);
    }

    public void removeUser(String id){
        userMapper.removeUser(id);
    }

    public void removeUserArr(String[] arr){
        for (String s : arr) {
            userMapper.removeUser(s);
        }
    }

    public void changeUser(User user){
        userMapper.changeUser(user);
    }

    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }
}
