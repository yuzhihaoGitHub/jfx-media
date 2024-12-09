package com.yuzhihao.learn.h2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.yuzhihao.learn.h2.entity.SysDevice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统设备表 服务类
 * </p>
 *
 * @author yuzhihao
 * @since 2024-11-15 11:40:37
 */
public interface ISysDeviceService extends IService<SysDevice> {

    /**
     * 获取设备信息
     * @param deviceId
     * @return
     */
    SysDevice getByDeviceId(String deviceId);

    /**
     * 获取设备信息
     * @param device
     * @return
     */
    PageInfo<SysDevice> lists(int pageIndex, SysDevice device);

    /**
     * 获取设备信息
     * @param device
     * @return
     */
    PageInfo<SysDevice> lists(int pageIndex,int pageSize, SysDevice device);

    /**
     * 获取设备信息
     * @param device
     * @return
     */
    PageInfo<SysDevice> listsOr(int pageIndex,int pageSize, SysDevice device);

}
