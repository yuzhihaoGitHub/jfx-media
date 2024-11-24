package com.yuzhihao.learn.hello.dialog;

import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.impl.charm.glisten.license.LicenseManager;
import com.gluonhq.impl.charm.glisten.util.NagScreenPresenter;
import com.yuzhihao.learn.Hello;
import com.yuzhihao.learn.javassist.AppManagerJavassist;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class ApplicationDialog {
    public static void showDialog(String titile, String text) {
        Platform.runLater(() -> {
            Dialog<?> nagDlg = new Dialog<>();
            ImageView gluonLogo = new ImageView(new Image(ApplicationDialog.class.getResource("/images/icon.png").toExternalForm()));
            gluonLogo.setSmooth(true);
            Label title = new Label(titile);
            title.setGraphic(gluonLogo);
            title.setContentDisplay(ContentDisplay.LEFT);
            nagDlg.setTitle(title);
            Label content = new Label(text);
            nagDlg.setContent(content);
            Button btn = new Button("关闭");
            btn.setOnAction((e) -> {
                nagDlg.hide();
            });
            nagDlg.getButtons().add(btn);
            nagDlg.showAndWait();
        });
    }

    public static void main(String[] args) {
        AppManagerJavassist.javassist();



    }

}
