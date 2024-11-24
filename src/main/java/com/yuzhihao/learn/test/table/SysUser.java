package com.yuzhihao.learn.test.table;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户信息表
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
@Data
public class SysUser  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（男 女 未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（正常 停用）
     */
    private String status;

    /**
     * 删除标志（代表存在 代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    public SysUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public SysUser(Long userId, String userName, String nickName, String email, String phonenumber, String sex) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId = " + userId +
                ", userName = " + userName +
                ", nickName = " + nickName +
                ", email = " + email +
                ", phonenumber = " + phonenumber +
                ", sex = " + sex +
                ", avatar = " + avatar +
                ", password = " + password +
                ", status = " + status +
                ", delFlag = " + delFlag +
                ", loginIp = " + loginIp +
                ", loginDate = " + loginDate +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
                "}";
    }
}
