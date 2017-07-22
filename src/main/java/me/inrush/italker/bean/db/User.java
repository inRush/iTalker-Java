package me.inrush.italker.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 用户的Model, 对应数据库
 * @author inrush
 * @date 2017/7/22.
 * @package me.inrush.italker.bean.db
 */
@Entity
@Table(name = "TB_USER")
public class User {
    // 主键
    @PrimaryKeyJoinColumn
    // 主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    // 不允许更改,不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    // 用户名唯一
    @Column(nullable = false,length = 128,unique = true)
    private String name;

    // 电话唯一
    @Column(nullable = false,length = 62,unique = true)
    private String phone;

    // 密码不为空
    @Column(nullable = false)
    private String password;

    // 头像允许为空
    @Column
    private String portrait;

    @Column
    private String describe;

    // 性别有初始值,所以不为空
    @Column(nullable = false)
    private int sex = 0;

    // token 可以拉取用户信息,所以token必须唯一
    @Column(unique = true)
    private String token;

    // 用户推送上来的设备ID
    @Column
    private String pushId;

    // 定义创建时间戳,在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义更新时间戳,在创建时已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // 最后一个收到消息的时间
    @Column
    private LocalDateTime lastReceivedAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getLastReceivedAt() {
        return lastReceivedAt;
    }

    public void setLastReceivedAt(LocalDateTime lastReceivedAt) {
        this.lastReceivedAt = lastReceivedAt;
    }
}
