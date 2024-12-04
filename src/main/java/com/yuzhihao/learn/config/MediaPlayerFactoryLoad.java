package com.yuzhihao.learn.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;

/**
 *
 * 启动时初始化视频工厂JNA加载，这个过程会很慢
 *
 * @author yuzhihao
 */
@Log4j2
@Configuration
public class MediaPlayerFactoryLoad{


    @PostConstruct
    public void init(){
        long start = System.currentTimeMillis();
        new MediaPlayerFactory();
        log.info("视频工厂初始化时长：{}",System.currentTimeMillis()-start);
    }

}

