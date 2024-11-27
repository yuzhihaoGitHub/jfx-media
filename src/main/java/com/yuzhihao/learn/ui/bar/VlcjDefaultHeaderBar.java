package com.yuzhihao.learn.ui.bar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.ui.ApplicationLayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 默认的UI HeaderBar
 *
 * @author yuzhihao
 */
@Log4j2
@AllArgsConstructor
public class VlcjDefaultHeaderBar {

    private final AppBar appBar;

    /**
     * 展示
     */
    public void show() {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> {
            AppManager.getInstance().getDrawer().open();
            log.info("nav icon");
        }));
        appBar.setTitleText("摄像头播放器");

        TextField search = new TextField();
        search.setPromptText("请输入摄像头名称");

        appBar.getActionItems().addAll(
                search,
                MaterialDesignIcon.SEARCH.button(e -> System.out.println("search")),
                MaterialDesignIcon.MENU.button(e -> {
                    icon();
                    System.out.println("menu");
                }),
                MaterialDesignIcon.FAVORITE.button(e -> {
                    System.out.println("fav");
                })
        );


        MenuItem settings = new MenuItem("Settings");

        settings.setGraphic(MaterialDesignIcon.SETTINGS.graphic());
        settings.setOnAction((e) -> {
            AppManager.getInstance().showLayer(ApplicationLayer.HOME_LAYER);
        });
        appBar.getMenuItems().addAll(settings, new MenuItem("Settings"), new MenuItem("Settings"));

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
