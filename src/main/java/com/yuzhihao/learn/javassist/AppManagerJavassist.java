package com.yuzhihao.learn.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.log4j.Log4j2;

/**
 *
 * 处理一下启动是打印AppManager 证书免费 弹窗
 *
 * @author yuzhihao
 */
@Log4j2
public class AppManagerJavassist {

    public static void javassist() {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("com.gluonhq.impl.charm.glisten.license.LicenseManager");
            // 获取静态代码块 将静态初始化方法的体设置为空
            cc.getClassInitializer().setBody("{}");
            // 获取静态方法并修改其实现
            CtMethod m = cc.getDeclaredMethod("validateLicense");
            m.setBody("{ System.out.println(\"LicenseManager停止开启AppManager免费证书弹窗\"); return \"LicenseManager\";}");
            cc.toClass();
            // 调用修改后的静态方法
//            System.out.println(LicenseManager.validateLicense("", null, null));

            cc = pool.get("com.gluonhq.impl.charm.glisten.tracking.TrackingManager");
            // 获取静态代码块 将静态初始化方法的体设置为空
            cc.getClassInitializer().setBody("{}");

            m = cc.getDeclaredMethod("trackUsage");
            m.setBody("{ System.out.println(\"TrackingManager停止开启AppManager免费证书弹窗\"); return true;}");
            cc.toClass();

        } catch (Exception e) {
            log.error("启动是打印AppManager证书免费异常：",e);
        }
    }

}
