package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j2;

/**
 * 摄像头列表页面
 *
 * @author yuzhihao
 * @since 2024-11-24 13:23:57
 *
 */
@Log4j2
public class CameraListView extends View {


    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        setCenter(show());
    }

    private Node show(){
        //登陆页面
        Label info = new Label("JVLC MEDIA PLAYER");
        info.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        return info;
    }



}
