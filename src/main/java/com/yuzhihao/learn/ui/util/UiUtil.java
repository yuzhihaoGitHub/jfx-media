package com.yuzhihao.learn.ui.util;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author yuzhihao
 */
public class UiUtil {


    public static Label label(String text) {
        return label(text,null);
    }

    public static Label label(String text, Node graphic) {
        Label label = new Label(text,graphic);
        label.setMinHeight(Region.USE_PREF_SIZE);
        label.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        return label;
    }

    public static HBox hbox(Node... nodes) {
        HBox hbox = new HBox(nodes);
        return hbox;
    }

    public static Node view404() {
        ImageView imageView = new ImageView("/images/404.jpeg");

        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        Label label = new Label("未找到页面!!!");
        VBox root = new VBox(20, imageView, label);
        root.setAlignment(Pos.CENTER);

        return root;
    }
}
