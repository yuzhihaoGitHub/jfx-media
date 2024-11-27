package com.yuzhihao.learn.h2.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统设备表
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "SYS_DEVICE", autoResultMap = true)
public class SysDevice extends Model<SysDevice> implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备唯一ID
     */
    private String deviceId;

    /**
     * 自定义设备名称
     */
    private String customName;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备厂商如：海康，大华等
     */
    private String manufacturer;

    /**
     * 设备厂商如：海康，大华等
     */
    private String company;

    /**
     * 设备Model
     */
    private String model;

    /**
     * 设备Firmware
     */
    private String firmware;

    /**
     * SIP传输协议
     */
    private String transport;

    /**
     * 流协议
     */
    private String streamTransport;

    /**
     * 码流帧数，用于传输分辨率和帧频控制 0 不实用 1 1级增强 2 3 类推
     */
    private String streamFrame;

    /**
     * 设备连接状态，0离线 1在线
     */
    private String onLine;

    /**
     * 设备主动注销状态，0未知，1，已注销 2在线
     */
    private String onLogOut;

    /**
     * 设备注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 设备最近一次心跳时间
     */
    private LocalDateTime keepaliveTime;

    /**
     * 设备IP
     */
    private String ip;

    /**
     * 设备端口
     */
    private Integer port;

    /**
     * 设备注册有效期
     */
    private Integer expires;

    /**
     * 设备IP地址
     */
    private String hostAddress;

    /**
     * 协议字符集
     */
    private String charset;

    /**
     * 设备SSRC临时值
     */
    private String ssrcVal;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 系统本地IP
     */
    private String localIp;


    private String username;


    private String password;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Override
    public String toString() {
        return "SysDevice{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", customName='" + customName + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", firmware='" + firmware + '\'' +
                ", transport='" + transport + '\'' +
                ", streamTransport='" + streamTransport + '\'' +
                ", onLine='" + onLine + '\'' +
                ", onLogOut='" + onLogOut + '\'' +
                ", registerTime=" + registerTime +
                ", keepaliveTime=" + keepaliveTime +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", expires=" + expires +
                ", hostAddress='" + hostAddress + '\'' +
                ", charset='" + charset + '\'' +
                ", ssrcVal='" + ssrcVal + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", localIp='" + localIp + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
