package com.yuzhihao.learn.ui.bar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.yuzhihao.learn.ui.ApplicationLayer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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


//                    Platform.runLater(()->{
//
//                        Stage newStage = new Stage();
//                        newStage.setTitle("新页面");
//                        // 设置为模态窗口
//                        newStage.initModality(Modality.APPLICATION_MODAL);
//
//                        // 创建新页面的内容
//                        StackPane newRoot = new StackPane();
//                        Button closeButton = new Button("关闭");
//                        closeButton.setOnAction(event -> {
//                            newStage.close();
//
//                            AppManager.getInstance().switchToPreviousView();
//                        }); // 关闭新窗口
//                        newRoot.getChildren().add(closeButton);
//
//                        newStage.setScene(new Scene(newRoot, 600, 400));
//                        newStage.show();
//                    });

                    System.out.println("fav");
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
                        log.info("退出登陆:{}",e);
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
