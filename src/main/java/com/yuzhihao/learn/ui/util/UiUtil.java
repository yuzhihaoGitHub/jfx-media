package com.yuzhihao.learn.ui.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author yuzhihao
 */
public class UiUtil {


    public static Label label(String text, Node graphic) {
        Label label = new Label(text,graphic);
        label.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        return label;
    }

    public static HBox hbox(Node... nodes) {
        HBox hbox = new HBox(nodes);
        return hbox;
    }

}
