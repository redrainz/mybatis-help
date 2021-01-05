package xyz.redrain.test.model;

import xyz.redrain.anntation.Column;
import xyz.redrain.anntation.Ignore;

import java.util.Date;

/**
 * Created by RedRain on 2018/10/27.
 *
 * @author RedRain
 * @version 1.0
 */
public class User {

    private Integer id;
    @Column(jdbcType = "varchar")
    private String username;
    private String password;
    private Date time;
    @Ignore
    private Date time1;

    private String userId;

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

}
