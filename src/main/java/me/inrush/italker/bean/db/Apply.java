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
@Table(name = "TB_APPLY")
public class Apply {

    public static final int TYPE_ADD_USER = 1; // 添加用户
    public static final int TYPE_ADD_GROUP = 2; // 添加群

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


    // 对申请信息进行描述
    @Column(nullable = false)
    private String description;

    // 附件 可为空
    // 可以附带图片的地址,或者其他
    @Column(columnDefinition = "TEXT")
    private String attach;

    // 当前申请的类型
    @Column(nullable = false)
    private int type;

    // 目标Id 不进行强关联,不建立主外键关系
    // type -> TYPE_ADD_USER : User.id
    // type -> TYPE_ADD_GROUP : GROUP.id
    @Column(nullable = false)
    private String targetId;

    // 申请人
    // 一个人可以有很多个申请
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;
    @Column(insertable = false,updatable = false)
    private String applicantId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
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
}
