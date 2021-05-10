package com.cxy.community.controller;

import com.cxy.community.entity.Content;
import com.cxy.community.entity.Label;
import com.cxy.community.service.ContentService;
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
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

// 内容管理

    @GetMapping("/getAllContent")
    public List<Content> getAllContent (){
        return contentService.getAllContent();
    }

    @GetMapping("/getContent")
    public List<Content> getContent(@RequestParam String type, @RequestParam String select){
        return contentService.getContent(type,select);
    }

    @GetMapping("/removeContent")
    public void removeContent(@RequestParam String id){
        contentService.removeContent(id);
    }

    @PostMapping("/removeContentArr")
    public void removeContentArr(@RequestBody String[] arr){
        for (int i = 0; i <  arr.length; i++) {
            System.out.println(arr[i]);
        }
        contentService.removeContentArr(arr);
    }

    @GetMapping("/changeContent")
    public void changeContent(@RequestParam String id, @RequestParam String state){
        contentService.changeContent(state,id);
    }


// 标签管理

    @GetMapping("/getAllLabel")
    public List<Label> getAllLabel(){
        return contentService.getAllLabel();
    }

    @PostMapping("/addLabel")
    public void addLabel(@RequestBody Label label){
        contentService.addLabel(label);
    }

    @GetMapping("/removeLabel")
    public void removeLabel(String id){
        contentService.removeContent(id);
    }

    @PostMapping("/removeLabelArr")
    public void removeLabelArr(@RequestBody String[] arr){
        contentService.removeContentArr(arr);
    }

    @PostMapping("/changeLabel")
    public void changeLabel(@RequestBody Label label){
        contentService.changeLabel(label);
    }

    @GetMapping("/toTop")
    public void toTop(String id){
        contentService.toTop(id);
    }

}
