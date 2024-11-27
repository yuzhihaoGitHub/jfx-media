package com.yuzhihao.learn.h2.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 指定要扫描的Mapper类的包的路径
 *
 * @author yuzhihao
 */
@Configuration
@MapperScan("com.yuzhihao.learn.**.mapper")
@Log4j2
public class MybatisPlusConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        log.debug("开始插入 createTime 填充,val:{}", getFieldValByName("createTime", metaObject));
        this.strictInsertFill(metaObject, "createBy", String.class, "admin");
        log.debug("开始插入 createBy 填充,val:{}", getFieldValByName("createBy", metaObject));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        log.debug("开始更新 updateTime 填充,val:{}", getFieldValByName("updateTime", metaObject));
        this.setFieldValByName("updateBy", "admin", metaObject);
        log.debug("开始更新 updateBy 填充,val:{}", getFieldValByName("updateBy", metaObject));
    }

}
