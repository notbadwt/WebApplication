package com.application.web.entity;

import com.application.security.model.UserDetails;

import java.util.List;


public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String status;
    private String type;
    private String name;
    private String unionId;
    private Long createDatetime;
    private Long lastLoginDatetime;

    private List<UserDocument> userDocumentList;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Long createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Long lastLoginDatetime) {

        this.lastLoginDatetime = lastLoginDatetime;
    }

    public List<UserDocument> getUserDocumentList() {
        return userDocumentList;
    }

    public void setUserDocumentList(List<UserDocument> userDocumentList) {
        this.userDocumentList = userDocumentList;
    }
}
