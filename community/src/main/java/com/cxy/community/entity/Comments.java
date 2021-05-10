package com.cxy.community.entity;

/**
 * @Author 小陈
 * @Date 2020/11/3 14:35
 * @Version 1.0
 */
public class Comments {

    private String id;
    private String account;
    private String name;
    private String oppositeSide;
    private String title;
    private String content;
    private int like;
    private String time;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOppositeSide() {
        return oppositeSide;
    }

    public void setOppositeSide(String oppositeSide) {
        this.oppositeSide = oppositeSide;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
