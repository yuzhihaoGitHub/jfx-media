package com.yuzhihao.learn.ui.init;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.yuzhihao.learn.ui.ApplicationView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

/**
 * 添加抽屉弹窗菜单目录
 *
 * @author yuzhihao
 */
@Log4j2
public class NavigationDrawerItemInit {

    public static void init() {
        NavigationDrawer drawer = AppManager.getInstance().getDrawer();

        VBox header = new VBox();
        header.setPrefHeight(100);
        header.setStyle("-fx-background-color: #57a5e1;");

        Label label = new Label("JVLC摄像头连接播放器");
        label.setStyle("-fx-font-size: 1.5em;-fx-text-fill: #ffffff;");
        Label label1 = new Label("专注于处理摄像头连接，通过IP账号密码，国标28181协议上报，SIP通信实时点播观看视频等");
        label1.setWrapText(true);
        label1.setStyle("-fx-font-size: 1em;-fx-text-fill: #ffffff;");
        VBox.setMargin(label, new Insets(10));
        VBox.setMargin(label1, new Insets(10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(label, label1);

        drawer.setHeader(header);

        drawer.getItems().add(getItem("/images/icons/menu/sy.png", "首页", ApplicationView.INDEX_VIEW));
        drawer.getItems().add(getItem("/images/icons/menu/sxtlb.png", "摄像头列表", ApplicationView.CAMERA_LIST));
        drawer.getItems().addAll( getItem("/images/icons/menu/dblb.png", "多播列表", ApplicationView.PLAY_GROUPS));


    }

    private static NavigationDrawer.ViewItem getItem(String url, String name,String viewName) {
        ImageView sxtlb = new ImageView(url);
        sxtlb.setSmooth(true);
        sxtlb.setFitHeight(16);
        sxtlb.setFitWidth(16);

        NavigationDrawer.ViewItem item = new NavigationDrawer.ViewItem(name, sxtlb,viewName);
        item.setAutoClose(true);
        return item;
    }

}
