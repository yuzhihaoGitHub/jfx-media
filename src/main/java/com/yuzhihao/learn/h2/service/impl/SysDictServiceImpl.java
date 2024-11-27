package com.yuzhihao.learn.h2.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuzhihao.learn.h2.entity.SysDict;
import com.yuzhihao.learn.h2.mapper.SysDictMapper;
import com.yuzhihao.learn.h2.service.ISysDictService;
import jakarta.annotation.PostConstruct;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

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
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {


    @PostConstruct
    public void loadDictCache() {
        System.out.println(list());
    }

    @Override
    public List<Pair<String, String>> manufacturer() {
        List<SysDict> list = list(Wrappers.lambdaQuery(SysDict.class).eq(SysDict::getDictType, "sys_manufacturer"));
        return list.stream().map(e-> new Pair<>(e.getDictLabel(), e.getDictValue())).collect(Collectors.toList());
    }
}
