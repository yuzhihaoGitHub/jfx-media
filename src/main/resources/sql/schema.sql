-- 系统设备表
CREATE TABLE sys_device
(
    id               BIGINT      NOT NULL AUTO_INCREMENT, -- ID
    device_id        VARCHAR(64) NOT NULL,                -- 设备唯一ID
    custom_name      VARCHAR(255)    DEFAULT NULL,        -- 自定义设备名称
    name             VARCHAR(128)    DEFAULT NULL,        -- 设备名称
    company          VARCHAR(128)    DEFAULT NULL,        -- 选择设备厂商
    manufacturer     VARCHAR(128)    DEFAULT NULL,        -- 设备厂商
    model            VARCHAR(64)     DEFAULT NULL,        -- 设备Model
    firmware         VARCHAR(64)     DEFAULT NULL,        -- 设备Firmware
    transport        VARCHAR(50)     DEFAULT NULL,        -- SIP传输协议
    stream_transport VARCHAR(50)     DEFAULT 'UDP',       -- 流协议
    stream_frame     VARCHAR(8)      DEFAULT '0',         -- 码流帧数
    on_line          VARCHAR(16)     DEFAULT '离线',      -- 设备连接状态
    on_log_out       VARCHAR(16)     DEFAULT '未知',      -- 设备注销状态
    register_time    DATETIME        DEFAULT NULL,        -- 设备注册时间
    keepalive_time   DATETIME        DEFAULT NULL,        -- 最近一次心跳时间
    ip               VARCHAR(64)     DEFAULT NULL,        -- 设备IP
    port             INT             DEFAULT NULL,        -- 设备端口
    expires          INT             DEFAULT NULL,        -- 设备注册有效期
    host_address     VARCHAR(50)     DEFAULT NULL,        -- 设备IP地址
    charset          VARCHAR(50)     DEFAULT NULL,        -- 协议字符集
    ssrc_val         VARCHAR(16)     DEFAULT NULL,        -- 设备SSRC临时值
    longitude        DECIMAL(20, 14) DEFAULT NULL,        -- 经度
    latitude         DECIMAL(20, 14) DEFAULT NULL,        -- 纬度
    local_ip         VARCHAR(50)     DEFAULT NULL,        -- 系统本地IP
    username         VARCHAR(512)    DEFAULT NULL,        -- ZLM媒体服务器拉流URL
    password         VARCHAR(512)    DEFAULT NULL,        -- ZLM 流状态
    create_time      DATETIME        DEFAULT NULL,        -- 创建时间
    update_time      DATETIME        DEFAULT NULL,        -- 更新时间
    create_by        VARCHAR(64)     DEFAULT NULL,        -- 创建人
    update_by        VARCHAR(64)     DEFAULT NULL,        -- 更新人
    PRIMARY KEY (id),
    UNIQUE (device_id)                                    -- 设备唯一ID
);


-- 系统字典表
CREATE TABLE sys_dict
(
    dict_id     BIGINT NOT NULL AUTO_INCREMENT, -- 字典编码
    dict_sort   INT          DEFAULT 0,         -- 字典排序
    dict_label  VARCHAR(128) DEFAULT '',        -- 字典标签
    dict_value  VARCHAR(128) DEFAULT '',        -- 字典键值
    dict_type   VARCHAR(128) DEFAULT '',        -- 字典类型
    css_class   VARCHAR(128) DEFAULT NULL,      -- 样式属性（其他样式扩展）
    list_class  VARCHAR(128) DEFAULT NULL,      -- 表格回显样式
    is_default  VARCHAR(16)  DEFAULT '是',      -- 是否默认（是 否）
    status      VARCHAR(16)  DEFAULT '正常',    -- 状态（正常 停用）
    create_by   VARCHAR(64)  DEFAULT '',        -- 创建者
    create_time DATETIME     DEFAULT NULL,      -- 创建时间
    update_by   VARCHAR(64)  DEFAULT '',        -- 更新者
    update_time DATETIME     DEFAULT NULL,      -- 更新时间
    remark      VARCHAR(512) DEFAULT NULL,      -- 备注
    PRIMARY KEY (dict_id)
);

