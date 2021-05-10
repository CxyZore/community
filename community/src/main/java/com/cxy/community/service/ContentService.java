package com.cxy.community.service;

import com.cxy.community.entity.Content;
import com.cxy.community.entity.Label;
import com.cxy.community.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/4 4:00
 * @Version 1.0
 */
@Service
public class ContentService {

    @Autowired(required = false)
    private ContentMapper contentMapper;

// 内容管理

    public List<Content> getAllContent(){
        return  contentMapper.getAllContent();
    }

    public List<Content> getContent(String type, String select){
        return contentMapper.getContent(type,select);
    }

    public void changeContent(String state , String id){
        contentMapper.changeContent(state,id);
    }

    public void removeContent(String id){
        contentMapper.removeContent(id);
    }

    public void removeContentArr(String[] arr){
        for (String s : arr) {
            removeContent(s);
        }
    }

// 标签管理

    public List<Label> getAllLabel(){
        return contentMapper.getAllLabel();
    }

    public void addLabel(Label label){
        contentMapper.addLabel(label);
    }

    public void removeLabel(String id){
        contentMapper.removeContent(id);
    }

    public void removeLabelArr(String[] arr){
        for (String s : arr) {
            removeContent(s);
        }
    }

    public void changeLabel(Label label){
        contentMapper.changeLabel(label);
    }

    public void toTop(String id){
        contentMapper.toTop(id);
    }


}
