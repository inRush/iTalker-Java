package me.inrush.italker.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 关注关系表
 *
 * @author inrush
 * @date 2017/7/23.
 * @package me.inrush.italker.bean.db
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {
    // 主键
    @PrimaryKeyJoinColumn
    // 主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // 不允许更改,不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    // 定义一个关注发起人
    // 不可选,必须存储
    @ManyToOne(optional = false)
    // 定义关联的表字段名为originId,对应User.id
    // 定义的是数据库中的存储字段
    @JoinColumn(name = "originId")
    private User origin;
    // 把这个列提取到我们的Model中
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    // 我关注的人的列表
    // 对应数据库表字段为TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    // 定义为懒加载,默认加载User信息的时候,并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 一对多
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();

    public Set<UserFollow> getFollowing() {
        return following;
    }

    // 关注我的人的列表
    @JoinColumn(name = "targetId")
    // 定义为懒加载,默认加载User信息的时候,并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();


    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    // 定义关注的目标
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;

    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    // 对被关注人的备注
    @Column
    private String alias;

    // 定义创建时间戳,在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义更新时间戳,在创建时已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

}
