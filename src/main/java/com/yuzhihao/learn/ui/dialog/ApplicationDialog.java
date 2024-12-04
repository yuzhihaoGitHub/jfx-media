package com.yuzhihao.learn.ui.dialog;

import com.gluonhq.charm.glisten.control.Dialog;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 *
 * 弹窗
 *
 * @author yuzhihao
 */
public class ApplicationDialog {
    public static void showDialog(String titile, String text) {
        Platform.runLater(() -> {
            Dialog<?> dialog = new Dialog<>();
            ImageView gluonLogo = new ImageView(new Image(Objects.requireNonNull(ApplicationDialog.class.getResource("/images/openduke.png")).toExternalForm()));
            gluonLogo.setSmooth(true);

            Label title = new Label(titile);
            title.setGraphic(gluonLogo);
            title.setContentDisplay(ContentDisplay.LEFT);

            dialog.setTitle(title);

            Label content = new Label(text);
            dialog.setContent(content);
            Button btn = new Button("关闭");
            btn.setOnAction((e) -> dialog.hide());
            dialog.getButtons().add(btn);

            dialog.showAndWait();
        });
    }

}
