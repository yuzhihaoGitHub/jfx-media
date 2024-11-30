package com.yuzhihao.learn;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.yuzhihao.learn.javassist.AppManagerJavassist;
import com.yuzhihao.learn.ui.ApplicationLayer;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.init.NavigationDrawerItemInit;
import com.yuzhihao.learn.ui.util.TablePageInfo;
import com.yuzhihao.learn.ui.view.CameraListView;
import com.yuzhihao.learn.ui.view.IndexView;
import com.yuzhihao.learn.ui.view.PlayGroupsView;
import com.yuzhihao.learn.ui.view.StreamConfigListView;
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

    public static ConfigurableApplicationContext context;

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

        appManager = AppManager.initialize(this::postInit);

        context = SpringApplication.run(VlcjApplication.class);

        appManager.addViewFactory(ApplicationView.HOME_VIEW, IndexView::new);
        appManager.addViewFactory(ApplicationView.INDEX_VIEW, IndexView::new);
        appManager.addViewFactory(ApplicationView.CAMERA_LIST, CameraListView::new);
        appManager.addViewFactory(ApplicationView.PLAY_GROUPS, PlayGroupsView::new);
        appManager.addViewFactory(ApplicationView.STREAM_CONFIG_LIST, StreamConfigListView::new);

        appManager.addLayerFactory(ApplicationLayer.HOME_LAYER, ()->{
            Label label = new Label("Hello World!");
            label.setTextFill(Color.BLUE);
            label.setFont(Font.font(50));
            VBox ownerNode = new VBox(label);
            ownerNode.setAlignment(Pos.CENTER);
            ownerNode.setPrefSize(appManager.getView().getWidth(),appManager.getView().getHeight());
            ownerNode.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            PopupView popupView = new PopupView(ownerNode);
            popupView.setContent(ownerNode);
            popupView.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

            return popupView;
        });

        NavigationDrawerItemInit.init();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("VLCJ MEDIA PLAYER");
        appManager.start(stage);

        // 监听场景的宽度变化
        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
//            log.info("监听场景的宽度变化, oldValue: {}, newValue: {}", oldValue, newValue);
        });

        // 监听场景的高度变化
        stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {

//            log.info("监听场景的高度变化, oldValue: {}, newValue: {}", oldValue, newValue);
        });
    }

    @Override
    public void stop() {
        context.close();
    }

}
