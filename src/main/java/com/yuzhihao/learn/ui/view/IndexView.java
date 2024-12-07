package com.yuzhihao.learn.ui.view;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.bar.VlcjDefaultHeaderBar;
import com.yuzhihao.learn.ui.dialog.SysDeviceDialog;
import com.yuzhihao.learn.ui.util.ImagesUtil;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 *
 * 登陆成功后首页
 *
 * @author yuzhihao
 */
@Log4j2
public class IndexView extends View {

    public IndexView() {
        FloatingActionButton fab = new FloatingActionButton();

        fab.showOn(this);
        fab.setOnAction(event -> {
            new SysDeviceDialog().show().ifPresent(type-> {
                if(type.equals(ButtonType.OK)) {
                    getAppManager().switchView(ApplicationView.CAMERA_LIST);
                }
            });
        });
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        new VlcjDefaultHeaderBar(appBar).show();
        setCenter(center());
    }

    private VBox center(){
        ImageView imageView = new ImageView(ImagesUtil.OPENDUKE_ICON);

        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Label label = new Label("Hello, Gluon Mobile!!!");
        VBox root = new VBox(20, imageView, label);
        root.setAlignment(Pos.CENTER);

        return root;
    }


}
