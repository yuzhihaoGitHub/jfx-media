package com.yuzhihao.learn;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.yuzhihao.learn.config.ThreadPoolEnum;
import com.yuzhihao.learn.javassist.AppManagerJavassist;
import com.yuzhihao.learn.ui.ApplicationLayer;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.init.NavigationDrawerItemInit;
import com.yuzhihao.learn.ui.view.*;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

/**
 *
 * media的jfx程序
 *
 * @author yuzhihao
 */
@Log4j2
@SpringBootApplication
public class VlcjApplication extends Application {

    private ConfigurableApplicationContext context;

    private AppManager appManager;

    public static void main(String[] args) {
        launch(args);
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

        if (Platform.isDesktop()) {
            Dimension2D dimension2D = DisplayService.create()
                    .map(DisplayService::getDefaultDimensions)
                    .orElse(new Dimension2D(1120, 720));
            scene.getWindow().setWidth(dimension2D.getWidth());
            scene.getWindow().setHeight(dimension2D.getHeight());
        }
    }

    @Override
    public void init() {
        AppManagerJavassist.javassist();

        ThreadPoolEnum.数据处理线程池.execute(()->{
            context = SpringApplication.run(VlcjApplication.class);
            MediaSplashView.progress(100,"加载完成！");
        });

        appManager = AppManager.initialize(this::postInit);

        appManager.addViewFactory(ApplicationView.HOME_VIEW, IndexView::new);
        appManager.addViewFactory(ApplicationView.INDEX_VIEW, IndexView::new);
        appManager.addViewFactory(AppManager.SPLASH_VIEW, MediaSplashView::new);
        appManager.addViewFactory(ApplicationView.CAMERA_LIST, CameraListView::new);
        appManager.addViewFactory(ApplicationView.PLAY_GROUPS, PlayGroupsView::new);
        appManager.addViewFactory(ApplicationView.STREAM_CONFIG_LIST, StreamConfigListView::new);

        NavigationDrawerItemInit.init();

    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("VLCJ多媒体播放器");
        appManager.start(stage);
    }

    @Override
    public void stop() {
        context.close();
    }

}
