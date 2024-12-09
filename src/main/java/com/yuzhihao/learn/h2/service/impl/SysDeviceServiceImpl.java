package com.yuzhihao.learn.h2.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzhihao.learn.h2.common.SysDictConstant;
import com.yuzhihao.learn.h2.entity.SysDevice;
import com.yuzhihao.learn.h2.mapper.SysDeviceMapper;
import com.yuzhihao.learn.h2.service.ISysDeviceService;
import com.yuzhihao.learn.ui.util.TablePageInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

//    @PostConstruct
    public void loadDictCache() {
        for (int i = 0; i < 200; i++) {
            SysDevice sysDevice = new SysDevice();
            sysDevice.setDeviceId(UUID.randomUUID().toString().toUpperCase().replace("-",""));
            sysDevice.setRegisterTime(LocalDateTime.now());
            sysDevice.setKeepaliveTime(LocalDateTime.now());
            sysDevice.setCustomName("办公室"+i);
            sysDevice.setIp(i %2 == 1 ? "192.168.4.200" : "192.168.4.224");


            sysDevice.setCompany("海康");
            sysDevice.setUsername("admin");
            sysDevice.setPassword("FJTech508");

            //rtsp://admin:FJTech508@192.168.4.200:554/h264/ch1/main/av_stream
            sysDevice.setProxyUrl("rtsp://".concat(sysDevice.getUsername())
                    .concat(":")
                    .concat(sysDevice.getPassword())
                    .concat("@")
                    .concat(sysDevice.getIp())
                    .concat(":554/h264/ch1/main/av_stream")
            );
            if(i %3 == 0){
                sysDevice.setMediaType(SysDictConstant.File);
                sysDevice.setProxyUrl("/Users/yuzhihao/Downloads/fengjing.mp4");
            }if(i %3 == 1){
                sysDevice.setMediaType(SysDictConstant.CAMERA);
            }if(i %3 == 2){
                sysDevice.setMediaType(SysDictConstant.WEB_URL);
                sysDevice.setProxyUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
            }

            sysDevice.insert();

        }
    }

    @Override
    public SysDevice getByDeviceId(String deviceId) {
        return getOne(Wrappers.lambdaQuery(SysDevice.class).eq(SysDevice::getDeviceId, deviceId));
    }

    @Override
    public PageInfo<SysDevice> lists(int pageIndex, SysDevice device) {
        return lists(pageIndex, TablePageInfo.pageSize, device);

    }

    @Override
    public PageInfo<SysDevice> lists(int pageIndex, int pageSize, SysDevice device) {
        PageHelper.startPage(pageIndex, pageSize);
        List<SysDevice> list = list(Wrappers.lambdaQuery(SysDevice.class)
                .like(StringUtils.hasLength(device.getIp()), SysDevice::getIp, device.getIp())
                .like(StringUtils.hasLength(device.getCustomName()), SysDevice::getCustomName, device.getCustomName())
        );
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysDevice> listsOr(int pageIndex, int pageSize, SysDevice device) {
        PageHelper.startPage(pageIndex, pageSize);
        List<SysDevice> list = list(Wrappers.lambdaQuery(SysDevice.class)
                .like(StringUtils.hasLength(device.getCustomName()), SysDevice::getCustomName, device.getCustomName())
                .or()
                .like(StringUtils.hasLength(device.getIp()),SysDevice::getIp, device.getIp())
        );
        return new PageInfo<>(list);
    }


}
