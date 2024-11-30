package com.yuzhihao.learn.h2.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "SYS_DICT",autoResultMap = true)
public class SysDict extends Model<SysDict> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    /**
     * 字典编码
     */
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（是 否）
     */
    private String isDefault;

    /**
     * 状态（正常 停用）
     */
    private String status;

    /**
     * 状态（正常 停用）
     */
    @TableField(exist = false)
    private String configType;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }


    @Override
    public String toString() {
        return "SysDict{" +
        "dictId = " + dictId +
        ", dictSort = " + dictSort +
        ", dictLabel = " + dictLabel +
        ", dictValue = " + dictValue +
        ", dictType = " + dictType +
        ", cssClass = " + cssClass +
        ", listClass = " + listClass +
        ", isDefault = " + isDefault +
        ", status = " + status +
        ", createBy = " + createBy +
        ", createTime = " + createTime +
        ", updateBy = " + updateBy +
        ", updateTime = " + updateTime +
        ", remark = " + remark +
        "}";
    }
}
