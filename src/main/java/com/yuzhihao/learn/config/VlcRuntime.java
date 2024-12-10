package com.yuzhihao.learn.config;

import lombok.extern.log4j.Log4j2;

/**
 *
 *
 * @author yuzhihao
 */
@Log4j2
public class VlcRuntime {

    public final static String JAVA_HOME = System.getProperty("java.home");
    public final static String USER_DIR = System.getProperty("user.dir");
    public final static String USER_HOME = System.getProperty("user.home");
    public final static String APPDATA = System.getenv("APPDATA");

    static {
        log.info("java.home:{}", JAVA_HOME);
        log.info("user.dir:{}", USER_DIR);
        log.info("user.home:{}", USER_HOME);
        log.info("APPDATA:{}", APPDATA);
    }

}
