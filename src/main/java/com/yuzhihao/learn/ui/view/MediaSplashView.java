package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.ProgressBar;
import com.gluonhq.charm.glisten.mvc.SplashView;
import com.yuzhihao.learn.ui.util.ImagesUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Objects;

/**
 * @author yuzhihao
 */
public class MediaSplashView extends SplashView {

    private long time = System.currentTimeMillis();

    private static final ProgressBar PROGRESS_BAR = new ProgressBar();
    private static final Label DYNAMIC_MESSAGE = new Label("");
    private static final Label PROMPT_MESSAGE = new Label("程序加载中，请稍后。。。");

    private Timeline timeline;

    public MediaSplashView() {
        setCenter(center());
    }

    public Node center() {
        ImageView imageView = new ImageView(ImagesUtil.BACK_BJ2);
        imageView.setPreserveRatio(false);

        // 绑定图片大小到舞台大小
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());

        // 创建根布局
        StackPane root = new StackPane();

        ImageView view = new ImageView(ImagesUtil.OPENDUKE_ICON);
        view.setFitHeight(200);
        view.setPreserveRatio(true);

        VBox vBox = new VBox(view, showProgress());
        vBox.setAlignment(Pos.CENTER);
        StackPane.setMargin(vBox, new Insets(100, 0, 0, 0));
        root.getChildren().addAll(imageView, vBox); // 将背景图片添加到布局中

        return root;

    }

    public static void progress(int progress, String content) {
        Platform.runLater(() -> {
            PROGRESS_BAR.setProgress(progress / 100f);
            PROMPT_MESSAGE.setText("资源加载中：" + content);
            if (progress == 100) {
                DYNAMIC_MESSAGE.setText("程序加载已完成！");
            } else {
                DYNAMIC_MESSAGE.setText("程序加载进度：" + progress + "%，请稍后");
            }
        });
    }

    private static int n = 0;

    private Node showProgress() {
        VBox vbox = new VBox();

        PROGRESS_BAR.setProgress(0.1);
        PROGRESS_BAR.prefWidthProperty().bind(widthProperty());
        PROGRESS_BAR.setMinHeight(30);

        VBox.setMargin(PROMPT_MESSAGE, new Insets(10, 0, 0, 0));
        VBox.setMargin(PROGRESS_BAR, new Insets(10, 0, 0, 0));

        vbox.getChildren().addAll(DYNAMIC_MESSAGE, PROMPT_MESSAGE, PROGRESS_BAR);

        vbox.setAlignment(Pos.BASELINE_LEFT);

        // 动态更新进度条
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            if(System.currentTimeMillis() - time > 1000 * 60){
                DYNAMIC_MESSAGE.setText("第一次数据初始化时间较长，大概在3～10分钟，请耐心等待！"+DYNAMIC_MESSAGE.getText());
                time = System.currentTimeMillis() + 1000 * 60 * 60;
            }
            if (PROGRESS_BAR.getProgress() == 1) {
                timeline.stop();
                hideSplashView();
                return;
            }
            switch (n) {
                case 0: {
                    DYNAMIC_MESSAGE.setText(DYNAMIC_MESSAGE.getText().replaceAll("。", "") + "。");
                    n++;
                    break;
                }
                case 1: {
                    DYNAMIC_MESSAGE.setText(DYNAMIC_MESSAGE.getText().replaceAll("。", "") + "。。");
                    n++;
                    break;
                }
                case 2: {
                    DYNAMIC_MESSAGE.setText(DYNAMIC_MESSAGE.getText().replaceAll("。", "") + "。。。");
                    n++;
                    break;
                }
                default: {
                    DYNAMIC_MESSAGE.setText(DYNAMIC_MESSAGE.getText().replaceAll("。", "") + "。。。。");
                    n = 0;
                    break;
                }
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // 无限循环
        timeline.play(); // 开始动画

        vbox.setPadding(new Insets(100));

        return vbox;
    }

}
