package com.yuzhihao.learn.h2.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.entity.SysDict;
import com.yuzhihao.learn.h2.mapper.SysDictMapper;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import com.yuzhihao.learn.h2.service.ISysDictService;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final ISysDeviceService deviceService;

    @Override
    public List<Pair<String, String>> manufacturer() {
        List<SysDict> list = list(Wrappers.lambdaQuery(SysDict.class).eq(SysDict::getDictType, "sys_manufacturer"));
        return list.stream().map(e -> new Pair<>(e.getDictLabel(), e.getDictValue())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> mediaType() {
        List<SysDict> list = list(Wrappers.lambdaQuery(SysDict.class).eq(SysDict::getDictType, "sys_media_type"));
        return list.stream().map(e -> new Pair<>(e.getDictLabel(), e.getDictValue())).collect(Collectors.toList());
    }

    @Override
    public boolean manufacturerInsert(SysDict dict) {
        long count = count(Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictType, "sys_manufacturer")
                .eq(SysDict::getDictLabel, dict.getDictLabel()));

        Assert.isTrue(count == 0,"该名称配置已存在");

        return dict.insert();
    }

    @Override
    public boolean manufacturerUpdate(SysDict dict) {
        long count = count(Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictType, "sys_manufacturer")
                .eq(SysDict::getDictLabel, dict.getDictLabel())
                .ne(SysDict::getDictId, dict.getDictId())
        );

        Assert.isTrue(count == 0,"该名称配置已存在");

        return dict.updateById();
    }

    @Override
    public SysDict manufacturerGet(String key) {
        return getOne(Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictType, "sys_manufacturer")
                .eq(SysDict::getDictLabel, key)
        );
    }

    @Override
    public List<SysDict> manufacturerList() {
        List<SysDict> list = list(Wrappers.lambdaQuery(SysDict.class).eq(SysDict::getDictType, "sys_manufacturer"));
        list.forEach(e -> e.setConfigType(e.getDictSort() != 999 ? "用户配置":"系统配置"));
        return list;
    }

    @Override
    public boolean delete(List<SysDict> dicts) {
        for (SysDict dict : dicts) {
            long count = deviceService.count(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getCompany, dict.getDictValue()));
            Assert.isTrue(count == 0, "该配置已被使用，无法删除");
            Assert.isTrue(dict.getDictSort() != 999, "系统配置无法删除，请取消系统配置");
        }
        return removeByIds(dicts.stream().map(SysDict::getDictId).toList());
    }

}
