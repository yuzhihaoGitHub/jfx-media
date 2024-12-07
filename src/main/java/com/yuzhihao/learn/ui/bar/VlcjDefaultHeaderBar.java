package com.yuzhihao.learn.ui.bar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.ui.ApplicationLayer;
import com.yuzhihao.learn.ui.ApplicationView;
import com.yuzhihao.learn.ui.view.MediaPlayerView;
import com.yuzhihao.learn.ui.view.play.PlayerView;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * 默认的UI HeaderBar
 *
 * @author yuzhihao
 */
@Log4j2
@AllArgsConstructor
public class VlcjDefaultHeaderBar {

    private final AppBar appBar;

    private final TextField search = new TextField();

    /**
     * 展示
     */
    public void show() {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> {
            AppManager.getInstance().getDrawer().open();
            log.info("nav icon");
        }));
        appBar.setTitleText("媒体播放器");
        search.setMinWidth(300);
        search.setPromptText("请输入媒体或播放地址：文件/URL");

        appBar.getActionItems().addAll(
                search,
                MaterialDesignIcon.SEARCH.button(e -> {
                    System.out.println("search");
                    AppManager.getInstance().getView().fireEvent(new PlayerView.MediaEvent(PlayerView.MediaEvent.CLOSE));
                    AppManager.getInstance().getView().getCenter().fireEvent(new PlayerView.MediaEvent(PlayerView.MediaEvent.CLOSE));
                    AppManager.getInstance().getView().fireEvent(new LifecycleEvent(AppManager.getInstance().getView(),LifecycleEvent.HIDDEN));
                    AppManager.getInstance().getView().getCenter().fireEvent(new LifecycleEvent(AppManager.getInstance().getView(),LifecycleEvent.HIDDEN));
//                    AppManager.getInstance().switchView(ApplicationView.MEDIA_PLAYER_VIEW);
                }),
                MaterialDesignIcon.PERSONAL_VIDEO.button(e -> {
                    if(StringUtils.hasLength(search.getText())){
                        ArrayList<String> list = new ArrayList<>();
                        list.add(search.getText());
                        MediaPlayerView.updatePlayer(list);
                        AppManager.getInstance().switchView(ApplicationView.MEDIA_PLAYER_VIEW);

                    }else{
                        new Toast("请输入媒体或播放地址：文件/URL！");
                    }

                    System.out.println("menu");
                })
        );


        MenuItem mi = new MenuItem("我的");
        mi.setGraphic(MaterialDesignIcon.ACCOUNT_CIRCLE.graphic());
        mi.setOnAction((e) -> AppManager.getInstance().showLayer(ApplicationLayer.HOME_LAYER));
        MenuItem logout = new MenuItem("退出登陆");
        logout.setGraphic(MaterialDesignIcon.CANCEL.graphic());
        logout.setOnAction((e) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要退出吗？");
                    alert.showAndWait().ifPresent(buttonType -> {
                        AppManager.getInstance().switchView(ApplicationView.HOME_VIEW);
                    });
                }
        );
        appBar.getMenuItems().addAll(mi, logout);
    }


    private void icon() {
        MaterialDesignIcon[] values = MaterialDesignIcon.values();

        VBox vBox = new VBox();

        for (MaterialDesignIcon value : values) {
            HBox hBox = new HBox();
            hBox.setPrefWidth(300);

            hBox.setAlignment(Pos.CENTER);
            Label label = new Label(value.name());

            Node graphic = value.graphic();
            graphic.setStyle("-fx-font-size: 2em;");
            HBox.setMargin(graphic, new Insets(0, 0, 0, 30));
            HBox.setMargin(graphic, new Insets(0, 0, 0, 30));


            hBox.getChildren().addAll(label, graphic);
            vBox.getChildren().add(hBox);
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitleText("所有的图标ICON");
        dialog.setAutoHide(true);
        dialog.setContent(new ScrollPane(vBox));
        dialog.showAndWait();
    }

}
