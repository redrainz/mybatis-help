package xyz.redrain.test;

import xyz.redrain.anntation.Table;

import java.util.Date;

@Table(propertyUseUnderlineStitching = false)
public class UserInfo {
    private Integer id;

    private String userName;

    private String password;

    private Date time;

    private Date time1;

    private String userId;

    private String bdContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBdContent() {
        return bdContent;
    }

    public void setBdContent(String bdContent) {
        this.bdContent = bdContent;
    }
}