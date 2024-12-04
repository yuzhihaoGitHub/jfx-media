package com.yuzhihao.learn.test;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AnchorPaneApp {

    private AnchorPane loadingOverlay;

    // 创建加载蒙层
    private AnchorPane createLoadingOverlay() {
        AnchorPane overlay = new AnchorPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // 半透明背景
        overlay.setVisible(false); // 初始隐藏

        Label loadingLabel = new Label("Loading...");

        AnchorPane.setLeftAnchor(loadingLabel, 0.0);
        AnchorPane.setRightAnchor(loadingLabel, 0.0);
        AnchorPane.setTopAnchor(loadingLabel, 0.0);
        AnchorPane.setBottomAnchor(loadingLabel, 0.0);

        overlay.getChildren().add(loadingLabel);
//        overlay.prefHeightProperty().bind(heightProperty());
        return overlay;
    }

    // 显示加载蒙层
    private void showLoadingOverlay() {
        loadingOverlay.setVisible(true);
    }

    // 隐藏加载蒙层
    private void hideLoadingOverlay() {
        loadingOverlay.setVisible(false);
    }
}
