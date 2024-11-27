package com.yuzhihao.learn.h2.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.mapper.SysDeviceMapper;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统设备表 服务实现类
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
@Service
public class SysDeviceServiceImpl extends ServiceImpl<SysDeviceMapper, SysDevice> implements ISysDeviceService {

    @Override
    public SysDevice getByDeviceId(String deviceId) {
        return getOne(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getDeviceId, deviceId));
    }

}
