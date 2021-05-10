package com.cxy.community.entity;

/**
 * @Author 小陈
 * @Date 2020/11/3 14:33
 * @Version 1.0
 */
public class Attention {
    private String id;
    private String accountName;
    private String account;
    private String otherAccountName;
    private String otherAccount;
    private String time;

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

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOtherAccountName() {
        return otherAccountName;
    }

    public void setOtherAccountName(String otherAccountName) {
        this.otherAccountName = otherAccountName;
    }
}
