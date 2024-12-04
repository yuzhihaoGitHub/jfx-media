package com.yuzhihao.learn.config;

import com.yuzhihao.learn.ui.view.MediaSplashView;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhihao
 */
@Configuration
@Log4j2
@AllArgsConstructor
public class BeanLoadListener implements BeanPostProcessor {

    private final ApplicationContext context;

    public static int loadedBeansCount = 21;
    private static int count = 0;

    @PostConstruct
    public void count() {
        count = context.getBeanDefinitionCount();
    }

    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        loadedBeansCount++;
        int progress = (int) ((loadedBeansCount / (float) count) * 100);
        if (bean != null) {
            MediaSplashView.progress(progress, bean.toString());
        }
        return bean;
    }

    /**
     * 进度
     *
     * @return
     */
    public static int progress() {
        return (int) ((loadedBeansCount / (float) count) * 100);
    }

}

