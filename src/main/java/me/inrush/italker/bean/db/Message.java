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
@Table(name = "TB_MESSAGE")
public class Message {
    public static final int TYPE_STR = 1; // 字符串类型
    public static final int TYPE_PIC = 2; // 图片类型
    public static final int TYPE_FILE = 3; // 文件类型
    public static final int TYPE_AUDIO = 4; // 语音类型

    // 主键
    @Id
    @PrimaryKeyJoinColumn
    // 这里不自动生成UUID,由客户端自动生成
    // 避免复杂的服务端和客户端的映射关系
    // 主键生成存储的类型为UUID
    // @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // 不允许更改,不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    // 内容不允许为空,类型为TEXT
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 附件
    @Column
    private String arttach;

    // 消息类型
    @Column(nullable = false)
    private int type;

    // 发送者 不为空
    // 多个消息对应一个发送者
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    private User sender;
    // 这个字段仅仅是为了对应sender 数据库段senderId
    // 不允许手动的更新或者插入
    @Column(nullable = false,updatable = false,insertable = false)
    private String senderId;

    // 接受者 可为空
    // 多个消息对应一个接受者
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(updatable = false,insertable = false)
    private String receiverId;

    // 一个群可以接收多个消息
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @Column(updatable = false,insertable = false)
    private String groupId;

    // 定义创建时间戳,在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义更新时间戳,在创建时已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArttach() {
        return arttach;
    }

    public void setArttach(String arttach) {
        this.arttach = arttach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
