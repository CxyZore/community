package com.cxy.community.controller;

import com.cxy.community.entity.*;
import com.cxy.community.service.FuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/9 13:28
 * @Version 1.0~
 */
@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/func")
public class FuncController {

    @Autowired(required = false)
    private FuncService funcService;

// 留言管理

    @GetMapping("/getAllMessage")
    public List<Message> getAllMessage(){
        return funcService.getAllMessage();
    }

    @GetMapping("/removeMessage")
    public void removeMessage(String id){
        System.out.println(id);
        funcService.removeMessage(id);
    }

    @PostMapping("/removeMessageArr")
    public void removeMessageArr(@RequestBody String[] arr){
        funcService.removeMessageArr(arr);
    }

    @GetMapping("/reply")
    public void reply(@RequestParam String id,@RequestParam String content){
        funcService.reply(id,content);
    }

// 推荐管理

    @GetMapping("/getSuperRecommend")
    public List<Recommend> getSuperRecommend(){
        return funcService.getSuperRecommend();
    }

    @GetMapping("/addSuperRecommend")
    public void addSuperRecommend(@RequestParam String id,@RequestParam String sort){
        funcService.addSuperRecommend(id,sort);
    }

    @GetMapping("/changeSort")
    public void changeSort( @RequestParam String id,@RequestParam String sort,@RequestParam String type){
        funcService.changeSort(id, sort, type);
    }

    @GetMapping("/removeRecommend")
    public void removeRecommend(String id){
        System.out.println(id);
        funcService.removeRecommend(id);
    }

// 评论/关注/收藏管理

    @GetMapping("/getAllComments")
    public List<Comments> getAllComments(){
        return funcService.getAllComments();
    }

    @GetMapping("/removeComments")
    public void removeComments(@RequestParam String id,@RequestParam String oppositeSide){
        funcService.removeComments(id, oppositeSide);
    }

    @GetMapping("/getAllAttention")
    public List<Attention> getAllAttention(){
        return funcService.getAllAttention();
    }

    @GetMapping("/removeAttention")
    public void removeAttention(@RequestParam String id,@RequestParam String otherAccount){

        funcService.removeAttention(id, otherAccount);
    }

    @GetMapping("/getAllCollect")
    public List<Collect> getAllCollect(){
        return funcService.getAllCollect();
    }

    @GetMapping("/removeCollect")
    public void removeCollect(@RequestParam String account,@RequestParam String articID){
        System.err.println(account+','+articID);
        funcService.removeCollect(account, articID);
    }

}
