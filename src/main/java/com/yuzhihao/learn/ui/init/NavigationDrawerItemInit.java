package com.yuzhihao.learn.ui.init;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.util.ImagesUtil;
import com.yuzhihao.learn.ui.util.UiUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        header.setPrefHeight(120);
        header.setStyle("-fx-background-color: #57a5e1;");

        Label title = new Label("JVLC摄像头连接播放器");
        title.setStyle("-fx-font-size: 1.5em;-fx-text-fill: #ffffff;");

        ImageView icon = new ImageView(ImagesUtil.CAMERA_ICON);

        Label descInfo = UiUtil.label("专注于处理摄像头连接，通过IP账号密码，国标28181协议上报，SIP通信实时点播观看视频等");
        descInfo.setStyle("-fx-wrap-text: true;-fx-font-size: 1em;-fx-text-fill: #ffffff;");

        VBox.setMargin(icon, new Insets(0,0,0,10));
        VBox.setMargin(title, new Insets(0,0,10,10));
        VBox.setMargin(descInfo, new Insets(0,0,10,10));
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(icon, title, descInfo);

        drawer.setHeader(header);

        drawer.getItems().add(getItem(ImagesUtil.MENU_SY_ICON, "首页", ApplicationView.INDEX_VIEW));
        drawer.getItems().add(getItem(ImagesUtil.MENU_SXTLB_ICON, "媒体列表", ApplicationView.CAMERA_LIST));
        drawer.getItems().add(getItem(ImagesUtil.MENU_LPZLB_ICON, "配置列表", ApplicationView.STREAM_CONFIG_LIST));
        drawer.getItems().addAll( getItem(ImagesUtil.MENU_DBLB_1_ICON, "播放列表", ApplicationView.PLAY_GROUPS));

    }

    private static NavigationDrawer.ViewItem getItem(Image image, String name, String viewName) {
        ImageView sxtlb = new ImageView(image);
        sxtlb.setSmooth(true);
        sxtlb.setFitHeight(16);
        sxtlb.setFitWidth(16);

        NavigationDrawer.ViewItem item = new NavigationDrawer.ViewItem(name, sxtlb,viewName);
        item.setAutoClose(true);
        return item;
    }

}
