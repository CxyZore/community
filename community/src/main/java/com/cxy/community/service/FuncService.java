package com.cxy.community.service;

import com.cxy.community.entity.*;
import com.cxy.community.mapper.FuncMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/4 4:21
 * @Version 1.0
 */
@Service
public class FuncService {

    @Autowired(required = false)
    private FuncMapper funcMapper;

// 留言管理

    public List<Message> getAllMessage(){
        return funcMapper.getAllMessage();
    }

    public void removeMessage(String id){
        funcMapper.removeMessage(id);
    }

    public void removeMessageArr(String[] arr){
        for (String s : arr) {
            funcMapper.removeMessage(s);
        }
    }

    public void reply(String id, String content){
        funcMapper.reply(id,content);
    }

// 推荐管理

    public List<Recommend> getSuperRecommend(){
        return funcMapper.getSuperRecommend();
    }

    public void addSuperRecommend(String id,String sort){
        funcMapper.addSuperRecommend(id,sort);
    }

    public void changeSort(String id, String sort, String type){
        funcMapper.changeSort(id, sort, type);
    }

    public void removeRecommend(String id){
        funcMapper.removeRecommend(id);
    }

// 评论/关注/收藏管理

    public List<Comments> getAllComments(){
        return funcMapper.getAllComments();
    }

    public void removeComments(String id,String oppositeSide){
        funcMapper.removeComments(id,oppositeSide);
    }

    public List<Attention> getAllAttention(){
        return funcMapper.getAllAttention();
    }

    public void removeAttention(String id,String otherAccount){
        funcMapper.removeAttention(id, otherAccount);
    }

    public List<Collect> getAllCollect(){
        return funcMapper.getAllCollect();
    }

    public void removeCollect(String account,String articID){
        funcMapper.removeCollect(account, articID);
    }


}
