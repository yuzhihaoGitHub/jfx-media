package com.yuzhihao.learn.ui.view.components;

import com.gluonhq.charm.glisten.application.AppManager;
import com.yuzhihao.learn.ui.util.ImagesUtil;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;

/**
 * 左右icon
 *
 * @author yuzhihao
 */
@Log4j2
public class LeftRightIcon {

    private final static ImageView rightIcon = new ImageView(ImagesUtil.RIGHT_ICON); // 替换为你的图标路径
    private final static ImageView leftIcon = new ImageView(ImagesUtil.LEFT_ICON); // 替换为你的图标路径
    private final static Pane pane = new Pane(leftIcon, rightIcon);

    public static void hide(boolean isLeft) {
        log.info(isLeft);
        if (isLeft) {
            leftIcon.setVisible(false);
            leftIcon.setManaged(false);
        } else {
            rightIcon.setVisible(false);
            rightIcon.setManaged(false);
        }
    }

    public static void hide() {
        log.info("hide:");
        leftIcon.setVisible(false);
        leftIcon.setManaged(false);
        rightIcon.setVisible(false);
        rightIcon.setManaged(false);
    }

    public static void show(boolean isLeft) {
        if (isLeft) {
            showLeft();
        } else {
            showRight();
        }
    }

    public static void showLeft() {
        transition(rightIcon, leftIcon);
    }

    public static void showRight() {
        transition(leftIcon, rightIcon);
    }

    /**
     * 切换左右图标
     *
     * @param hide
     * @param show
     */
    private static void transition(ImageView hide, ImageView show) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), hide);
        fadeOut.setFromValue(1.0); // 从完全可见
        fadeOut.setToValue(0.0); // 到完全不可见
        fadeOut.setOnFinished(e -> {
            hide.setVisible(false);
            hide.setManaged(false); // 动画结束后设置为不可见
        });
        fadeOut.play(); // 播放淡出动画

        show.setManaged(true);
        show.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), show);
        fadeIn.setFromValue(0.0); // 从完全不可见
        fadeIn.setToValue(1.0); // 到完全可见
        fadeIn.play(); // 播放淡入动画
    }

    /**
     * 开启ICON
     *
     * @param left  点击左边事件
     * @param right 点击右边事件
     */
    public static void open(EventHandler<? super MouseEvent> left, EventHandler<? super MouseEvent> right) {

        rightIcon.setFitWidth(20); // 设置图标宽度
        rightIcon.setFitHeight(20); // 设置图标高度
        rightIcon.setPreserveRatio(true);

        leftIcon.setFitWidth(20); // 设置图标宽度
        leftIcon.setFitHeight(20); // 设置图标高度
        leftIcon.setPreserveRatio(true);

        //谈入
        rightIcon.setOnMouseClicked(right);
        //谈出
        leftIcon.setOnMouseClicked(left);

        pane.setPrefSize(20, 20);
        pane.setMaxSize(20, 20);

        pane.translateYProperty().bind(AppManager.getInstance().getView().heightProperty().divide(4));


        AppManager.getInstance().getGlassPane().getChildren().add(pane);
    }

    public static void close() {
        AppManager.getInstance().getGlassPane().getChildren().remove(pane);
    }


}
