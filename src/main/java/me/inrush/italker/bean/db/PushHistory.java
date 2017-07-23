package me.inrush.italker.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author inrush
 * @date 2017/7/23.
 * @package me.inrush.italker.bean.db
 */
@Entity
@Table(name = "TB_PUSH_HISTORY")
public class PushHistory {
    // 主键
    @Id
    @PrimaryKeyJoinColumn
    // 主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // 不允许更改,不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    // 推送的具体实体存储的JSON字符串
    // BLOB是比TEXT更大的一个大字段类型
    @Lob
    @Column(nullable = false,columnDefinition = "BLOB")
    private String entity;

    // 推送的实体类型
    @Column(nullable = false)
    private int entityType;


    // 消息的接受者
    // 一个用户可以接收很多消息
    // 加载一个推送消息的时候,直接加载一个用户的信息
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(nullable = false,updatable = false,insertable = false)
    private String receiverId;


    // 消息的发送者,可为空,因为可能是系统的消息
    // 一个用户可以接收很多消息
    // 加载一个推送消息的时候,直接加载一个用户的信息
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;
    @Column(updatable = false,insertable = false)
    private String senderId;


    // 接受者的pushId 可为空
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


    // 消息送达的时间 可为空
    @Column
    private LocalDateTime arrivalAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(LocalDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }
}
