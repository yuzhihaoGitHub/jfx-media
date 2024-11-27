package com.yuzhihao.learn.h2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuzhihao.learn.h2.entity.SysDict;
import javafx.util.Pair;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 获取厂商 k v
     * @return
     */
    public List<Pair<String, String>> manufacturer();

}
